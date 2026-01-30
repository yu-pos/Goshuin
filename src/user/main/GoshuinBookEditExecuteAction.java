package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import dao.GoshuinBookDao;
import dao.RegdGoshuinBookDesignDao;
import tool.Action;

public class GoshuinBookEditExecuteAction extends Action {

    private static final String EDITING_BOOK_KEY = "editingGoshuinBook";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Integer bookId = parseIntOrNull(req.getParameter("bookId"));
        if (bookId == null) {
            req.setAttribute("error", "御朱印帳が特定できませんでした（bookIdがありません）");
            req.getRequestDispatcher("GoshuinBookView.action").forward(req, res);
            return;
        }

        Integer designId = parseIntOrNull(req.getParameter("designId")); // ← nullでもOK

        GoshuinBookDao bookDao = new GoshuinBookDao();
        RegdGoshuinBookDesignDao designDao = new RegdGoshuinBookDesignDao();

        GoshuinBook book = bookDao.getById(bookId);
        if (book == null) {
            req.setAttribute("error", "御朱印帳が見つかりませんでした");
            req.getRequestDispatcher("GoshuinBookView.action").forward(req, res);
            return;
        }

        // designId が指定されている時だけデザインを差し替える
        if (designId != null) {
            book.setGoshuinBookDesign(designDao.getById(designId));
        }

        HttpSession session = req.getSession();
        session.setAttribute(EDITING_BOOK_KEY, book);

        res.sendRedirect("GoshuinBookStickerEdit.action?bookId=" + bookId);
    }

    private Integer parseIntOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
