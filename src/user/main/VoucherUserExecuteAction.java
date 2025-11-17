package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.Voucher;
import dao.VoucherDao;
import tool.Action;

public class VoucherUserExecuteAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{
		// セッション/ログインチェック
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        VoucherDao voucherDao = new VoucherDao();

        try {
            // 商品券IDを取得して使用済みに更新
            int voucherId = Integer.parseInt(req.getParameter("voucherId"));
            boolean success = voucherDao.update(voucherId);

            if (success) {
                // 成功 → 完了画面へ
                res.sendRedirect("voucher_use_complete.jsp");
                return;
            } else {
                // 更新失敗 → エラーメッセージを表示して一覧画面へ戻す
                req.setAttribute("errorMessage", "商品券の使用処理に失敗しました。");
            }

        } catch (Exception e) {
            // 例外 → エラーメッセージを表示して一覧画面へ戻す
            req.setAttribute("errorMessage", "商品券の使用中にエラーが発生しました。");
        }

        // 商品券一覧を再取得
        List<Voucher> vouchers = voucherDao.searchById(user.getId());
        req.setAttribute("vouchers", vouchers);
        req.setAttribute("hasVoucher", !vouchers.isEmpty());

        // jspへフォワード
        req.getRequestDispatcher("/voucher.jsp").forward(req, res);

	}
}
