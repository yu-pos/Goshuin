package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.GoshuinBook;
import bean.RegdGoshuinBookDesign;
import dao.GoshuinBookDao;
import dao.RegdGoshuinBookDesignDao;
import tool.Action;

public class GoshuinBookEditExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        // パラメータ取得
        int bookId;
        int designId;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
            designId = Integer.parseInt(req.getParameter("designId"));
        } catch (Exception e) {
            // うまく取れなければ色選択画面に戻す
            req.setAttribute("error", "表紙デザインが正しく選択されていません。");
            String url = "GoshuinBookEdit.action";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        GoshuinBookDao gdao = new GoshuinBookDao();
        GoshuinBook goshuinBook = gdao.getById(bookId);
        if (goshuinBook == null) {
            req.setAttribute("error", "御朱印帳が見つかりません。");
            String url = "GoshuinBookEdit.action";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // 選択されたデザインを設定
        RegdGoshuinBookDesignDao ddao = new RegdGoshuinBookDesignDao();
        RegdGoshuinBookDesign design = ddao.getById(designId);
        if (design == null) {
            req.setAttribute("error", "選択された表紙デザインが存在しません。");
            String url = "GoshuinBookEdit.action?bookId=" + bookId;
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        goshuinBook.setGoshuinBookDesign(design);

        // ステッカー情報はそのまま（goshuinBookDao.update がステッカーも更新する）
        gdao.update(goshuinBook);

        // 表紙デザイン確定後は、ステッカー編集画面へリダイレクト
        res.sendRedirect("GoshuinBookStickerEdit.action?bookId=" + bookId);
    }
}
