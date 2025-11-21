package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.OwnedGoshuinBookSticker;
import bean.User;
import dao.GoshuinBookDao;
import dao.OwnedGoshuinBookStickerDao;
import tool.Action;

public class GoshuinBookStickerEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String url = "user/main/goshuin_book_sticker_edit.jsp";

        HttpSession session = req.getSession(false);
        if (session == null) {
            url = "user/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            url = "user/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
        } catch (Exception e) {
            // 取得失敗 → アクティブ御朱印帳
            if (user.getActiveGoshuinBook() != null) {
                bookId = user.getActiveGoshuinBook().getId();
            } else {
                req.setAttribute("error", "編集対象の御朱印帳が指定されていません。");
                req.getRequestDispatcher(url).forward(req, res);
                return;
            }
        }

        GoshuinBookDao gdao = new GoshuinBookDao();
        GoshuinBook goshuinBook = gdao.getById(bookId);
        if (goshuinBook == null) {
            req.setAttribute("error", "御朱印帳が見つかりません。");
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // 所持ステッカー一覧（本当は「所持しているものだけ」にしたい場合はこちら）
        OwnedGoshuinBookStickerDao osdao = new OwnedGoshuinBookStickerDao();
        List<OwnedGoshuinBookSticker> ownedStickerList = osdao.searchByUser(user.getId());
        req.setAttribute("ownedStickerList", ownedStickerList);

        req.setAttribute("goshuinBook", goshuinBook);

        req.getRequestDispatcher(url).forward(req, res);
    }
}
