package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class VoucherUseAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		  // セッション/ログインチェック
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // voucher_use.jsp にフォワード
        req.getRequestDispatcher("/voucher_use.jsp").forward(req, res);

	}
}
