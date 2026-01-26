package operator.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class PasswordChangeExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String url = "";
        String newPassword = req.getParameter("newPassword");
        String newPasswordConfirm = req.getParameter("newPasswordConfirm");

        OperatorDao operatorDao = new OperatorDao();
        List<String> errors = new ArrayList<>();

        HttpSession session = req.getSession(false);
        Operator operator = (Operator) (session != null ? session.getAttribute("operator") : null);

        if (operator == null) {
            errors.add("ログイン情報が失われました。再度ログインしてください。");
            req.setAttribute("errors", errors);
            url = "login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // 未入力チェック
        if (newPassword == null || newPassword.isEmpty()) {
            errors.add("このフィールドに入力してください（新しいパスワード）");
        }
        if (newPasswordConfirm == null || newPasswordConfirm.isEmpty()) {
            errors.add("このフィールドに入力してください（新しいパスワード（確認））");
        }

        // パスワード一致チェック
        if (errors.isEmpty() && !newPassword.equals(newPasswordConfirm)) {
            errors.add("パスワードが一致しません");
        }

        // エラーがあれば戻す
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            url = "password_change.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        try {
            // パスワード更新
            operator.setPassword(newPassword);
            operator.setFirstLoginCompleted(true);

            boolean success = operatorDao.update(operator);

            if (success) {
                session.setAttribute("operator", operator);
                url = "password_change_complete.jsp";
            } else {
                errors.add("パスワードの変更に失敗しました");
                req.setAttribute("errors", errors);
                url = "password_change.jsp";
            }

        } catch (Exception e) {
            errors.add("パスワードの変更に失敗しました");
            req.setAttribute("errors", errors);
            url = "password_change.jsp";
        }

        req.getRequestDispatcher(url).forward(req, res);
    }
}