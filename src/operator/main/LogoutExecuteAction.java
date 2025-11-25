package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutExecuteAction extends Action{

	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッションを取得
        HttpSession session = req.getSession(false);

        if (session != null) {
            // セッションから運営者情報を削除
            session.removeAttribute("operator");

            // 必要ならセッション自体を破棄
            session.invalidate();
        }

        // ログアウト完了画面へフォワード
        req.getRequestDispatcher("operator_logout_complete.jsp").forward(req, res);


	}
}
