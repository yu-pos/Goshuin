package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.GoshuinBookStickerAttachment;
import dao.GoshuinBookDao;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;

public class GoshuinBookStickerEditExecuteAction extends Action {

    private static final String EDITING_BOOK_KEY = "editingGoshuinBook";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        GoshuinBookDao bookDao = new GoshuinBookDao();
        RegdGoshuinBookStickerDao stickerDao = new RegdGoshuinBookStickerDao();

        int bookId = Integer.parseInt(req.getParameter("bookId"));

        GoshuinBook book = (GoshuinBook) session.getAttribute(EDITING_BOOK_KEY);
        if (book == null || book.getId() != bookId) {
            // 念のため
            book = bookDao.getById(bookId);
        }

        // 送信されたステッカー情報を GoshuinBook に詰め直す
        String[] stickerIds = req.getParameterValues("stickerId");
        String[] xPosArray  = req.getParameterValues("xPos");
        String[] yPosArray  = req.getParameterValues("yPos");

        List<GoshuinBookStickerAttachment> attachList = new ArrayList<>();

        if (stickerIds != null) {
            for (int i = 0; i < stickerIds.length; i++) {
                if (stickerIds[i] == null || stickerIds[i].isEmpty()) continue;

                GoshuinBookStickerAttachment att = new GoshuinBookStickerAttachment();
                att.setGoshuinBookId(bookId);

                int stickerId = Integer.parseInt(stickerIds[i]);
                att.setGoshuinBookSticker(stickerDao.getById(stickerId));

                double x = Double.parseDouble(xPosArray[i]);
                double y = Double.parseDouble(yPosArray[i]);
                att.setxPos(x);
                att.setyPos(y);
                att.setRotation(0.0); // 回転をまだ使っていないなら 0

                attachList.add(att);
            }
        }

        book.setAttachedStickerList(attachList);

        // ここで初めて DB 更新！（表紙デザインも含めて）
        bookDao.update(book);

	     // ・・・ステッカー情報を book に詰めて bookDao.update(book) したあと

	     // 編集用セッションを消す
	     session.removeAttribute("editingGoshuinBook");

	     // ✅ 完了画面用の Action に飛ばす
	     res.sendRedirect("GoshuinBookEditComplete.action?bookId=" + bookId);

    }
}
