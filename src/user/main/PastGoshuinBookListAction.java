package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.User;
import dao.GoshuinBookDao;
import tool.Action;

public class PastGoshuinBookListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            res.sendRedirect("Login.action");
            return;
        }

        GoshuinBookDao bookDao = new GoshuinBookDao();
        List<GoshuinBook> allBooks = bookDao.searchByUser(user.getId());
        System.out.println("[DEBUG] allBooks.size = " + allBooks.size());

        List<GoshuinBook> pastBooks = new ArrayList<>();

        int activeBookId = -1;
        if (user.getActiveGoshuinBook() != null) {
            activeBookId = user.getActiveGoshuinBook().getId();
            System.out.println("[DEBUG] activeBookId = " + activeBookId);
        }

        for (GoshuinBook b : allBooks) {
            System.out.println("[DEBUG] loop bookId = " + b.getId());
            if (b.getId() != activeBookId) {
                pastBooks.add(b);
                System.out.println("[DEBUG]  -> added as past");
            }
        }

        req.setAttribute("bookList", pastBooks);
        req.getRequestDispatcher("past_goshuin_book_list.jsp").forward(req, res);
    }
}
