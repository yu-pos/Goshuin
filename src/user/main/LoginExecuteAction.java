package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class LoginExecuteAction extends Action{

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

            //ポイント付与処理

            // ✅ 成功時は main.jsp へ
            url = "main.jsp";

        } else {
            // 認証失敗の場合
            List<String> errors = new ArrayList<>();
            errors.add("電話番号またはパスワードが確認できませんでした");
            req.setAttribute("errors", errors);

            // 入力された電話番号をセット（再表示用）
            req.setAttribute("tel", telNumber);

            // ✅ 失敗時は「今回貼ってくれたログイン画面の JSP」へ戻す
            //   ここは実際のパスに合わせて下さい（例：user/login.jsp など）
            url = "login.jsp";
        }

        // フォワード
        req.getRequestDispatcher(url).forward(req, res);
    }
}
