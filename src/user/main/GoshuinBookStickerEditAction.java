package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.OwnedGoshuinBookSticker;
import dao.GoshuinBookDao;
import dao.OwnedGoshuinBookStickerDao;
import tool.Action;

public class GoshuinBookStickerEditAction extends Action {

    private static final String EDITING_BOOK_KEY = "editingGoshuinBook";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int bookId = Integer.parseInt(req.getParameter("bookId"));

        HttpSession session = req.getSession();
        GoshuinBook book = (GoshuinBook) session.getAttribute(EDITING_BOOK_KEY);

        GoshuinBookDao bookDao = new GoshuinBookDao();

        if (book == null || book.getId() != bookId) {
            // セッションに何もなければ DB の状態をそのまま使う
            book = bookDao.getById(bookId);
        }

        // ステッカー一覧（本当は所持している分だけにするなら DAO を変える）
        OwnedGoshuinBookStickerDao ownedStickerDao = new OwnedGoshuinBookStickerDao();
        int userId = ((bean.User)session.getAttribute("user")).getId();
        List<OwnedGoshuinBookSticker> ownedStickerList = ownedStickerDao.searchByUser(userId);

        req.setAttribute("goshuinBook", book);
        req.setAttribute("ownedStickerList", ownedStickerList);

        req.getRequestDispatcher("goshuin_book_sticker_edit.jsp").forward(req, res);
    }
}
