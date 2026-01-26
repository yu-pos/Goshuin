package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.OwnedVoucher;
import bean.User;
import dao.OwnedVoucherDao;
import tool.Action;

public class VoucherUseAction extends Action {

	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }


        User user = (User) session.getAttribute("user");
        String voucherIdStr = req.getParameter("voucherId");
        if (voucherIdStr == null) {
            req.setAttribute("errorMessage", "商品券IDが指定されていません。");
            req.getRequestDispatcher("voucher.action").forward(req, res);
            return;
        }

        int voucherId = Integer.parseInt(voucherIdStr);
        OwnedVoucherDao dao = new OwnedVoucherDao();
        OwnedVoucher voucher = dao.getById(voucherId);

        if (voucher == null || voucher.getUserId() != user.getId()) {
            req.setAttribute("errorMessage", "指定された商品券が見つからないか、利用できません。");
            req.getRequestDispatcher("Voucher.action").forward(req, res);
            return;
        }

        req.setAttribute("voucher", voucher);
        req.getRequestDispatcher("/user/main/voucher_use.jsp").forward(req, res);
    }
}

