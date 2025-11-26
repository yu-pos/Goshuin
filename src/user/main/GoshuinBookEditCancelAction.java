package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class GoshuinBookEditCancelAction extends Action {

    private static final String EDITING_BOOK_KEY = "editingGoshuinBook";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(EDITING_BOOK_KEY);
        }

        res.sendRedirect("GoshuinBookView.action");
    }
}
