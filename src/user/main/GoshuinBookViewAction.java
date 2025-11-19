package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.User;
import dao.GoshuinBookDao;
import tool.Action;

public class GoshuinBookViewAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        GoshuinBookDao goshuinBookDao = new GoshuinBookDao();
        GoshuinBook goshuinBook = null;

        // ★ User が持っている activeGoshuinBook の ID を元に、
        //    Dao から「中身付き」の御朱印帳を取得する
        if (user.getActiveGoshuinBook() != null) {
            int bookId = user.getActiveGoshuinBook().getId();
            goshuinBook = goshuinBookDao.getById(bookId);
        }

        // JSP に渡す
        req.setAttribute("goshuinBook", goshuinBook);

        // ↓ こう直す
        req.getRequestDispatcher("goshuin_book_view.jsp").forward(req, res);
    }
}
