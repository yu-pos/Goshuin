package operator.main;

import java.sql.SQLException;
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

        // ===== 入力チェック =====
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

        // IDを数値に変換（ここは NumberFormatException が起きうるが、今回はDB系とは分ける）
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            errors.add("運営者番号は数値で入力してください");
            req.setAttribute("errors", errors);
            req.setAttribute("id", idStr);
            req.getRequestDispatcher("login.jsp").forward(req, res);
            return;
        }

        // ===== DBアクセスが絡むので try-catch =====
        try {
            // 認証処理
            operator = operatorDao.login(id, password);

            if (operator != null && operator.isEnable()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("operator", operator);

                // 初回ログイン判定
                if (!operator.isFirstLoginCompleted()) {
                    // 初回ログイン → パスワード変更画面
                    url = "password_change.jsp";
                } else {
                    // 通常ログイン成功 → メイン画面
                    url = "main.jsp";
                }

            } else {
                // 認証失敗（ID/PW違い or 無効ユーザー）
                errors.add("運営者番号またはパスワードが間違っています");
                req.setAttribute("errors", errors);
                req.setAttribute("id", idStr);
                url = "login.jsp";
            }

            req.getRequestDispatcher(url).forward(req, res);
            return;

        } catch (Exception e) {
            // ===== DB未起動などの例外をここで捕まえる =====

            // （任意）SQLExceptionかどうかを辿る（ログ用途）
            Throwable cause = e;
            while (cause != null && !(cause instanceof SQLException)) {
                cause = cause.getCause();
            }

            // 利用者向けメッセージは固定でOK
            errors.add("データベースが起動していません。管理者に連絡するか、しばらくしてから再度お試しください。");

            // デバッグ用ログ（画面には出さない）
            System.out.println("[DEBUG](OperatorLoginExecute) DB error: "
                    + e.getClass().getName() + " / " + e.getMessage());

            req.setAttribute("errors", errors);
            req.setAttribute("id", idStr);
            req.getRequestDispatcher("login.jsp").forward(req, res);
            return;
        }
    }
}
