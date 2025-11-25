package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class OperatorDisableConfirmAction extends Action {
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

        // DAOを使って対象の運営者アカウントを取得
        OperatorDao dao = new OperatorDao();
        Operator targetOperator = dao.getById(id);

        // JSPに渡すためリクエスト属性にセット
        req.setAttribute("targetOperator", targetOperator);

        // 無効化確認画面へフォワード
        req.getRequestDispatcher("operator_disable_confirm.jsp").forward(req, res);

    }
}