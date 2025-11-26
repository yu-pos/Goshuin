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

        int bookId   = Integer.parseInt(req.getParameter("bookId"));
        int designId = Integer.parseInt(req.getParameter("designId"));

        GoshuinBookDao bookDao = new GoshuinBookDao();
        RegdGoshuinBookDesignDao designDao = new RegdGoshuinBookDesignDao();

        // 元の御朱印帳（DBの状態）
        GoshuinBook book = bookDao.getById(bookId);

        // 選択したデザインをセット（まだDBには書かない）
        book.setGoshuinBookDesign(designDao.getById(designId));

        // セッションに「編集中御朱印帳」として保持
        HttpSession session = req.getSession();
        session.setAttribute(EDITING_BOOK_KEY, book);

        // このあとステッカー配置画面のActionに投げる想定
        res.sendRedirect("GoshuinBookStickerEdit.action?bookId=" + bookId);
    }
}
