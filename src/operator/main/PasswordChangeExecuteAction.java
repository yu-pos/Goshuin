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
		// TODO 自動生成されたメソッド・スタブ

		String url = "";
        String newPassword = req.getParameter("newPassword");
        OperatorDao operatorDao = new OperatorDao();
        List<String> errors = new ArrayList<>();

        HttpSession session = req.getSession(false);
        Operator operator = (Operator) (session != null ? session.getAttribute("operator") : null);

        if (operator == null) {
            // セッション切れなど → ログイン画面へ戻す
            errors.add("ログイン情報が失われました。再度ログインしてください。");
            req.setAttribute("errors", errors);
            url = "login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // 未入力チェック
        if (newPassword == null || newPassword.isEmpty()) {
            errors.add("このフィールドに入力してください（新しいパスワード）");
            req.setAttribute("errors", errors);
            url = "password_change.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        try {
            // パスワード更新
            operator.setPassword(newPassword);
            operator.setFirstLoginCompleted(true); // 初回ログイン完了フラグを更新

            boolean success = operatorDao.update(operator);

            if (success) {
                // 変更完了 → 完了画面へ
                session.setAttribute("operator", operator); // 更新後の情報をセッションに反映
                url = "password_change_complete.jsp";
            } else {
                // 更新失敗
                errors.add("パスワードの変更に失敗しました");
                req.setAttribute("errors", errors);
                url = "password_change.jsp";
            }

        } catch (Exception e) {
            // 更新失敗
            errors.add("パスワードの変更に失敗しました");
            req.setAttribute("errors", errors);
            url = "password_change.jsp";
        }

        req.getRequestDispatcher(url).forward(req, res);



	}

}
