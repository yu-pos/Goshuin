package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.Event;
import dao.EventDao;
import tool.Action;
import tool.ImageUtils;

public class EventRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	// セッション確認
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("operator") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

    	req.setCharacterEncoding("UTF-8");

        try {
            // フォームから値を取得
            String title = req.getParameter("title");
            String text = req.getParameter("text");
            Part imagePart = req.getPart("image"); // <input type="file" name="image">

            // 画像必須チェック
            if (imagePart == null || imagePart.getSize() == 0) {
                req.setAttribute("errorMessage", "画像は必須です。");
                req.getRequestDispatcher("/operator/main/event_regist.jsp")
                   .forward(req, res);
                return;
            }

            // 画像保存処理
            String savedFilename = ImageUtils.saveImage(imagePart, "event", req);

            // Eventインスタンス生成
            Event event = new Event();
            event.setTitle(title);
            event.setText(text);
            event.setImagePath(savedFilename);

            // DB登録
            EventDao dao = new EventDao();
            boolean success = dao.insert(event);

            if (success) {
                // 登録成功 → 完了画面へ
                req.getRequestDispatcher("/operator/main/event_regist_complete.jsp")
                   .forward(req, res);
            } else {
                // 登録失敗 → 入力画面へ戻す
                req.setAttribute("errorMessage", "登録に失敗しました。");
                req.getRequestDispatcher("/operator/main/event_regist.jsp")
                   .forward(req, res);
            }

        } catch (Exception e) {
            // 例外発生時も失敗扱い
            req.setAttribute("errorMessage", "登録に失敗しました。");
            req.getRequestDispatcher("/operator/main/event_regist.jsp")
               .forward(req, res);
        }
    }
}