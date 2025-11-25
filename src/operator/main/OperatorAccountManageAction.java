package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class OperatorAccountManageAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	HttpSession session = req.getSession(false);
        Operator loginUser = (Operator) session.getAttribute("operator");

        // 管理者権限チェック
        if (loginUser == null || !loginUser.isAdmin()) {
            // 権限がない場合はメイン画面へリダイレクト
            session.setAttribute("errorMessage", "ページを表示する権限がありません。");
            res.sendRedirect("main.jsp");
            return;
        }

    	// リクエストパラメータから対象の運営者IDを取得
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);

        // 対象の運営者アカウントを取得
        OperatorDao dao = new OperatorDao();
        Operator operator = dao.getById(id);

        // JSPに渡すためリクエスト属性にセット
        req.setAttribute("operator", operator);

        // 管理メニュー画面へフォワード
        req.getRequestDispatcher("operator_account_manage.jsp").forward(req, res);
    }
}