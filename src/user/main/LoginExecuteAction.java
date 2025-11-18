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

        telNumber = req.getParameter("tel");
        password = req.getParameter("password");

        user = userDao.login(telNumber, password);

        if (user != null) { // 認証成功の場合
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // ★ 現在日時（ログイン日時）
            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalDate today = nowDateTime.toLocalDate();

            // ★ 前回ログイン日時（null の可能性あり）
            LocalDateTime oldDateTime = user.getLastLoginAt();
            boolean shouldGivePoint = false;

            if (oldDateTime == null) {
                // 初回ログイン
                shouldGivePoint = true;
            } else {
                LocalDate oldDate = oldDateTime.toLocalDate();
                // 前回ログインの日付と今日の日付が違う → 昨日以前 → 今日の初回ログイン
                if (!oldDate.isEqual(today)) {
                    shouldGivePoint = true;
                }
            }

            if (shouldGivePoint) {
                int addPoint = 1; // 付与ポイント（仕様に合わせて変更OK）

                // ★ メモリ上の user にポイントを加算
                user.setPoint(user.getPoint() + addPoint);

                // メイン画面に出すメッセージをセット
                req.setAttribute("loginPointMessage", "ログインポイントが付与されました");
            }

            // ★ 最終ログイン日時は、ポイント付与してもしなくても「今」に更新
            user.setLastLoginAt(nowDateTime);

            // ★ DB を更新（ポイント + last_login_at などをまとめて保存）
            userDao.update(user);

            // ★ セッション内の user も最新状態にしておく
            session.setAttribute("user", user);

            // メイン画面へ
            url = "main.jsp";

        } else {
            // 認証失敗の場合
            List<String> errors = new ArrayList<>();
            errors.add("電話番号またはパスワードが確認できませんでした");
            req.setAttribute("errors", errors);

            // 入力された電話番号をセット（再表示用）
            req.setAttribute("tel", telNumber);

            url = "login.jsp";
        }

        req.getRequestDispatcher(url).forward(req, res);
    }
}
