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

		// 商品券一覧を取得
		List<Voucher> vouchers = null;
		VoucherDao voucherDao = new VoucherDao();
		vouchers = voucherDao.searchByUserId(user.getId());

		// jspへフォワード
		req.setAttribute("vouchers", vouchers);
		req.setAttribute("hasVoucher", !vouchers.isEmpty()); // trueなら商品券あり、falseならなし
		req.getRequestDispatcher("/voucher.jsp").forward(req, res);

	}
}
