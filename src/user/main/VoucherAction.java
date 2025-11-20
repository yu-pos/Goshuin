package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.Voucher;
import dao.VoucherDao;
import tool.Action;

public class VoucherAction extends Action {

	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
				res.sendRedirect("login.jsp");
				return;
		}

//		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
//		UserDao userDao = new UserDao();
//		HttpSession session = req.getSession(true);
//		session.setAttribute("user", userDao.login("111-1111-1111", "test"));

		User user = (User) session.getAttribute("user");

        VoucherDao dao = new VoucherDao();
        List<Voucher> vouchers = dao.searchByUserId(user.getId());

        req.setAttribute("vouchers", vouchers);
        req.setAttribute("hasVoucher", !vouchers.isEmpty());
        req.getRequestDispatcher("/user/main/voucher.jsp").forward(req, res);
    }
}