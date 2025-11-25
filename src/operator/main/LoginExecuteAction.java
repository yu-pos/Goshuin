package operator.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String url = "";
        String idStr = req.getParameter("id");       // 運営者番号
        String password = req.getParameter("password");
        OperatorDao operatorDao = new OperatorDao();
        Operator operator = null;

        List<String> errors = new ArrayList<>();

        // 入力チェック（未入力）
        if (idStr == null || idStr.isEmpty()) {
            errors.add("このフィールドに入力してください（運営者番号）");
        }
        if (password == null || password.isEmpty()) {
            errors.add("このフィールドに入力してください（パスワード）");
        }

        // 入力不備があれば login.jsp に戻す
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("id", idStr);
            url = "login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // IDを数値に変換
        int id = Integer.parseInt(idStr);

        try {
            // 認証処理
            operator = operatorDao.login(id, password);

            if (operator != null && operator.isEnable()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("operator", operator);

                // 初回ログイン判定
                if (!operator.isFirstLoginCompleted()) {
                    // 初回ログイン → パスワード変更画面へ
                    url = "password_change.jsp";
                } else {
                    // 通常ログイン成功 → メイン画面へ
                    url = "main.jsp";
                }

            } else {
                // 認証失敗
                errors.add("運営者番号またはパスワードが間違っています");
                req.setAttribute("errors", errors);
                req.setAttribute("id", idStr);
                url = "login.jsp";
            }

        } catch (Exception e) {
            // 例外発生 → ログイン失敗
            errors.add("ログインに失敗しました");
            req.setAttribute("errors", errors);
            req.setAttribute("id", idStr);
            url = "login.jsp";
        }


        req.getRequestDispatcher(url).forward(req, res);
    }
}