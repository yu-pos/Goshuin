package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class EventRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	// セッション確認
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("operator") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

    	// そのままイベント登録画面へフォワード
        req.getRequestDispatcher("/operator/main/event_regist.jsp").forward(req, res);
    }
}