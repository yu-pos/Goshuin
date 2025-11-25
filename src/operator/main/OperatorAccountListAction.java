package operator.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class OperatorAccountListAction extends Action {
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


        // 全ての運営者アカウントを取得
        OperatorDao dao = new OperatorDao();
        List<Operator> operators = dao.getAll(null);

        // JSPに渡すためリクエスト属性にセット
        req.setAttribute("operators", operators);

        // 一覧画面へフォワード
        req.getRequestDispatcher("operator_account_list.jsp").forward(req, res);
    }
}