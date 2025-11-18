package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.Voucher;
import dao.VoucherDao;
import tool.Action;

public class VoucherUseAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		  // セッション/ログインチェック
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // voucherId パラメータを取得
        String voucherIdStr = req.getParameter("voucherId");
        if (voucherIdStr == null) {
            req.setAttribute("errorMessage", "商品券IDが指定されていません。");
            req.getRequestDispatcher("/voucher.jsp").forward(req, res);
            return;
        }

        int voucherId = Integer.parseInt(voucherIdStr);

        // 商品券情報を取得（getByIdを使用）
        VoucherDao voucherDao = new VoucherDao();
        Voucher voucher = voucherDao.getById(voucherId);

        // voucher_use.jsp にフォワード
        req.setAttribute("voucher", voucher);
        req.getRequestDispatcher("/voucher_use.jsp").forward(req, res);

	}
}
