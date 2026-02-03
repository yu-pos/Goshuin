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
            session.setAttribute("errorMessage", "ページを表示する権限がありません。");
            res.sendRedirect("main.jsp");
            return;
        }

        // リクエストパラメータから対象の運営者IDを取得
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("errorMessage", "不正なリクエストです。");
            res.sendRedirect("OperatorAccountList.action");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "不正なリクエストです。");
            res.sendRedirect("OperatorAccountList.action");
            return;
        }

        // ✅ 追加：ログイン中の自分自身は管理画面を開けない
        if (id == loginUser.getId()) {
            session.setAttribute("errorMessage", "ログイン中のアカウントは管理できません。");
            res.sendRedirect("OperatorAccountList.action");
            return;
        }

        // 対象の運営者アカウントを取得
        OperatorDao dao = new OperatorDao();
        Operator operator = dao.getById(id);

        // 対象が存在しない場合
        if (operator == null) {
            session.setAttribute("errorMessage", "指定された運営者アカウントが見つかりません。");
            res.sendRedirect("OperatorAccountList.action");
            return;
        }

        // JSPに渡すためリクエスト属性にセット
        req.setAttribute("operator", operator);

        // 管理メニュー画面へフォワード
        req.getRequestDispatcher("operator_account_manage.jsp").forward(req, res);
    }
}
