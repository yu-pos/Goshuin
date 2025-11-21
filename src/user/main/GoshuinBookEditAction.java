package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.GoshuinBook;
import bean.RegdGoshuinBookDesign;
import bean.RegdGoshuinBookSticker;
import dao.GoshuinBookDao;
import dao.RegdGoshuinBookDesignDao;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;

public class GoshuinBookEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        // どの御朱印帳を編集するか（パラメータ優先）
        String bookIdStr = req.getParameter("goshuinBookId");
        int goshuinBookId = 0;

        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            goshuinBookId = Integer.parseInt(bookIdStr);
        } else if (user.getActiveGoshuinBook() != null) {
            goshuinBookId = user.getActiveGoshuinBook().getId();
        }

        // ID が取れなかった場合はエラー扱い
        if (goshuinBookId == 0) {
            req.setAttribute("errorMessage", "編集対象の御朱印帳が指定されていません。");
            req.getRequestDispatcher("goshuin_book_view.jsp").forward(req, res);
            return;
        }

        GoshuinBookDao goshuinBookDao = new GoshuinBookDao();
        RegdGoshuinBookDesignDao designDao = new RegdGoshuinBookDesignDao();
        RegdGoshuinBookStickerDao stickerDao = new RegdGoshuinBookStickerDao();

        // 対象の御朱印帳（デザイン・ステッカー付き）を取得
        GoshuinBook goshuinBook = goshuinBookDao.getById(goshuinBookId);

        // 選択可能な御朱印帳デザイン一覧
        List<RegdGoshuinBookDesign> designList = designDao.searchAll();

        // 選択可能なステッカー一覧
        List<RegdGoshuinBookSticker> stickerList = stickerDao.searchAll();

        // JSP へ渡す
        req.setAttribute("goshuinBook", goshuinBook);
        req.setAttribute("designList", designList);
        req.setAttribute("stickerList", stickerList);

        req.getRequestDispatcher("goshuin_book_edit.jsp").forward(req, res);
    }
}
