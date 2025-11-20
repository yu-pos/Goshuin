package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import bean.User;
import dao.VoucherDao;
import tool.Action;

public class VoucherUseExecuteAction extends Action {

	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

//      //セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
//      	UserDao userDao = new UserDao();
//      	HttpSession session = req.getSession(true);
//      	session.setAttribute("user", userDao.login("111-1111-1111", "test"));

        //User user = (User) session.getAttribute("user");
        VoucherDao dao = new VoucherDao();

        try {
            int voucherId = Integer.parseInt(req.getParameter("voucherId"));
            boolean success = dao.update(voucherId);

            if (success) {
                //res.sendRedirect("/user/main/voucher_use_complete.jsp");
                req.getRequestDispatcher("/user/main/voucher_use_complete.jsp").forward(req, res);
                return;
            } else {
                req.setAttribute("errorMessage", "商品券の使用処理に失敗しました。");
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "商品券の使用中にエラーが発生しました。");
        }

        req.getRequestDispatcher("Voucher.action").forward(req, res);
    }
}