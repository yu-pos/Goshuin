package user.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String url = "";
        String telNumber = "";
        String password = "";
        UserDao userDao = new UserDao();
        User user = null;

        // 入力値取得
        telNumber = req.getParameter("tel");
        password  = req.getParameter("password");

        System.out.println("[DEBUG](LoginExecute) telNumber = " + telNumber);
        System.out.println("[DEBUG](LoginExecute) password = " + password);

        // エラーリスト（ログイン画面は List<String> でOK）
        List<String> errors = new ArrayList<>();

        // 🔹 電話番号形式チェック（10〜11桁の数字）
        if (telNumber == null || !telNumber.matches("\\d{10,11}")) {
            errors.add("有効な電話番号を入力してください");
        }

        // 電話番号形式に問題があれば、その時点でログイン画面へ戻す
        if (!errors.isEmpty()) {
            System.out.println("[DEBUG](LoginExecute) ログイン失敗-不正な電話番号");
            req.setAttribute("errors", errors);
            req.setAttribute("tel", telNumber);
            url = "login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // ✅ ここから下が DBアクセスが絡むので try-catch で囲う
//        try {
            // 🔹 認証処理
            user = userDao.login(telNumber, password);

            if (user != null) { // 認証成功の場合
                System.out.println("[DEBUG](LoginExecute)ログイン成功");
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);

                // ★ ログインポイント処理
                LocalDateTime nowDateTime = LocalDateTime.now();
                LocalDate today = nowDateTime.toLocalDate();

                LocalDateTime oldDateTime = user.getLastLoginAt();
                boolean shouldGivePoint = false;

                if (oldDateTime == null) {
                    shouldGivePoint = true;
                } else {
                    LocalDate oldDate = oldDateTime.toLocalDate();
                    if (!oldDate.isEqual(today)) {
                        shouldGivePoint = true;
                    }
                }

                if (shouldGivePoint) {
                    int addPoint = 1;
                    user.setPoint(user.getPoint() + addPoint);
                    req.setAttribute("loginPointMessage", "ログインポイントが付与されました");
                }

                // ログインした時刻を最終ログインに更新
                user.setLastLoginAt(nowDateTime);

                // ★ DB更新（ここもDB停止時に落ちる可能性がある）
                userDao.update(user);

                session.setAttribute("user", user);

                url = "Main.action";

            } else {
                // 認証失敗の場合（電話番号形式はOKだが、ユーザーがいない or パスワード不一致）
                errors.add("電話番号またはパスワードが確認できませんでした");
                req.setAttribute("errors", errors);
                req.setAttribute("tel", telNumber);
                url = "login.jsp";
            }

            req.getRequestDispatcher(url).forward(req, res);
            return;

//        } catch (Exception e) {
//            // ✅ DB未起動などの例外をここで拾って「ログイン画面」に表示する
//
//            // より丁寧にしたい場合：SQLException系を優先判定
//            Throwable cause = e;
//            while (cause != null && !(cause instanceof SQLException)) {
//                cause = cause.getCause();
//            }
//
//            // ここはメッセージ固定でOK（推奨ではないけど要件通り）
//            errors.add("データベースが起動していません。管理者に連絡するか、しばらくしてから再度お試しください。");
//
//            // （任意）デバッグ用：サーバログにだけ出す
//            System.out.println("[DEBUG](LoginExecute) DB error: " + e.getClass().getName() + " / " + e.getMessage());
//
//            req.setAttribute("errors", errors);
//            req.setAttribute("tel", telNumber);
//            req.getRequestDispatcher("login.jsp").forward(req, res);
//            return;
//        }
    }
}
