package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.User;
import dao.GoshuinBookDao;
import tool.Action;

public class GoshuinBookViewAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ▼ 最初から絶対パスにする
        String url = "/user/main/goshuin_book_view.jsp";

        HttpSession session = req.getSession(false);
        if (session == null) {
            req.getRequestDispatcher("/user/main/login.jsp").forward(req, res);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            req.getRequestDispatcher("/user/main/login.jsp").forward(req, res);
            return;
        }

        // アクティブ御朱印帳の ID 取得（User に保持している前提）
        int bookId = 0;

        if (user.getActiveGoshuinBook() != null) {
            bookId = user.getActiveGoshuinBook().getId();
        } else {
            // 所持している御朱印帳から一番新しいものを取る
            GoshuinBookDao gdao = new GoshuinBookDao();
            List<GoshuinBook> list = gdao.searchByUser(user.getId());
            if (!list.isEmpty()) {
                bookId = list.get(0).getId();
            }
        }

        if (bookId == 0) {
            req.setAttribute("goshuinBook", null);
        } else {
            GoshuinBookDao gdao = new GoshuinBookDao();
            GoshuinBook goshuinBook = gdao.getById(bookId);
            req.setAttribute("goshuinBook", goshuinBook);
        }

        // ▼ ここも絶対パス
        req.getRequestDispatcher(url).forward(req, res);
    }
}
