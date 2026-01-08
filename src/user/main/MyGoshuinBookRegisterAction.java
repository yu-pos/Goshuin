package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.User;
import dao.GoshuinBookDao;
import dao.UserDao;
import tool.Action;

public class MyGoshuinBookRegisterAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect("Login.action");
            return;
        }

        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            res.sendRedirect("Login.action");
            return;
        }

        String bookIdStr = req.getParameter("bookId");
        if (bookIdStr == null) {
            res.sendRedirect("PastGoshuinBookList.action");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);

        GoshuinBookDao gdao = new GoshuinBookDao();
        GoshuinBook book = gdao.getById(bookId);

        // 自分の御朱印帳か一応チェック
        if (book == null || book.getUserId() != loginUser.getId()) {
            res.sendRedirect("PastGoshuinBookList.action");
            return;
        }

     // DBの my_goshuin_book_id を更新
        UserDao udao = new UserDao();
        User updatedUser = udao.getById(loginUser.getId());

        updatedUser.setMyGoshuinBook(book);
        udao.update(updatedUser);

        // セッションのUserにも反映
        session.setAttribute("user", updatedUser);

        // ▼ PastGoshuinBookList 用のデータを再取得する
        GoshuinBookDao gdao2 = new GoshuinBookDao();
        java.util.List<GoshuinBook> bookList = gdao2.searchByUser(loginUser.getId());

        // request にセット
        req.setAttribute("bookList", bookList);
        req.setAttribute("message", "My御朱印帳を更新しました。");

        // ▼ 指定 JSP にフォワード（画面遷移しない）
        req.getRequestDispatcher("/user/main/past_goshuin_book_list.jsp").forward(req, res);

    }
}
