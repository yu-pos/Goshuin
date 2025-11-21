package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.GoshuinBook;
import bean.GoshuinBookStickerAttachment;
import dao.GoshuinBookDao;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;

public class GoshuinBookStickerEditExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        int bookId;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
        } catch (Exception e) {
            // bookId が取れない → 一旦ビューに戻す
            res.sendRedirect("GoshuinBookView.action");
            return;
        }

        GoshuinBookDao gdao = new GoshuinBookDao();
        GoshuinBook goshuinBook = gdao.getById(bookId);
        if (goshuinBook == null) {
            res.sendRedirect("GoshuinBookView.action");
            return;
        }

        // 送信されたステッカー情報（複数）
        String[] stickerIds = req.getParameterValues("stickerId");
        String[] xPosArray = req.getParameterValues("xPos");
        String[] yPosArray = req.getParameterValues("yPos");

        List<GoshuinBookStickerAttachment> newList = new ArrayList<>();

        if (stickerIds != null && xPosArray != null && yPosArray != null) {
            RegdGoshuinBookStickerDao sdao = new RegdGoshuinBookStickerDao();

            for (int i = 0; i < stickerIds.length; i++) {
                if (stickerIds[i] == null || stickerIds[i].isEmpty()) continue;
                if (xPosArray[i] == null || xPosArray[i].isEmpty()) continue;
                if (yPosArray[i] == null || yPosArray[i].isEmpty()) continue;

                try {
                    int stickerId = Integer.parseInt(stickerIds[i]);
                    double x = Double.parseDouble(xPosArray[i]);
                    double y = Double.parseDouble(yPosArray[i]);

                    GoshuinBookStickerAttachment att = new GoshuinBookStickerAttachment();
                    att.setGoshuinBookId(bookId);
                    att.setGoshuinBookSticker(sdao.getById(stickerId));
                    att.setxPos(x);
                    att.setyPos(y);
                    att.setRotation(0.0);  // とりあえず回転は0固定

                    newList.add(att);
                } catch (Exception ignore) {
                    // パース失敗したものはスキップ
                }
            }
        }

        // 新しいステッカーリストで置き換え
        goshuinBook.setAttachedStickerList(newList);

        // 更新（GoshuinBookStickerAttachmentDao.update が内部で走る）
        gdao.update(goshuinBook);

        // 保存後は御朱印帳ビューへ
        res.sendRedirect("GoshuinBookView.action");
    }
}
