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

	public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

//		// セッション/ログインチェック
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
            // 未ログインならログイン画面へリダイレクト
            res.sendRedirect("login.jsp");
            return;
        }

		User user = (User) session.getAttribute("user");

//		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
//		UserDao userDao = new UserDao();
//		HttpSession session = req.getSession(true);
//		//session.setAttribute("user", userDao.login("111-1111-1111", "test"));
//		User user = userDao.login("111-1111-1111", "test"); // User型を受け取る
//      session.setAttribute("user", user);

		// 商品券一覧を取得
		List<Voucher> vouchers = null;
		VoucherDao voucherDao = new VoucherDao();
		vouchers = voucherDao.searchById(user.getId());

		// jspへフォワード
		req.setAttribute("vouchers", vouchers);
		req.setAttribute("hasVoucher", !vouchers.isEmpty()); // trueなら商品券あり、falseならなし
		req.getRequestDispatcher("/voucher.jsp").forward(req, res);

	}
}
