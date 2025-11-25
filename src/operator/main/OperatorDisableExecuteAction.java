package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class OperatorDisableExecuteAction extends Action {
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
        Operator targetOperator = dao.getById(id);

        if (targetOperator != null) {
            // アカウントを無効化（isEnableをfalseに設定）
            targetOperator.setEnable(false);

            // DBを更新
            boolean success = dao.update(targetOperator);

            if (success) {
                req.setAttribute("message", "アカウントを無効化しました");
            } else {
                req.setAttribute("message", "アカウントの無効化に失敗しました");
            }
        } else {
            req.setAttribute("message", "対象のアカウントが存在しません");
        }

        // 完了画面に対象のユーザーを渡す
        req.setAttribute("targetOperator", targetOperator);

        // 完了画面へフォワード
        req.getRequestDispatcher("operator_disable_complete.jsp").forward(req, res);
    }
}