package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class GoshuinBookEditCompleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 必要ならメッセージだけセット
        req.setAttribute("message", "御朱印帳のカスタマイズを保存しました。");

        // 完了画面へフォワード
        req.getRequestDispatcher("goshuin_book_edit_complete.jsp")
           .forward(req, res);
    }
}
