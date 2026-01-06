package operator.main;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.tuple.Pair;

import bean.Operator;
import dao.OperatorDao;
import tool.Action;

public class OperatorRegistExecuteAction extends Action {

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

        String url = "operator_regist_confirm.jsp";
        List<String> errors = new ArrayList<>();

        OperatorDao operatorDao = new OperatorDao();

        try {
            // すべての運営者アカウントを取得
            List<Operator> operators = operatorDao.getAll(null);

            // 最大IDを探す
            int maxId = 0;
            for (Operator op : operators) {
                if (op.getId() > maxId) {
                    maxId = op.getId();
                }
            }
            int nextId = maxId + 1;

            // ランダムパスワード生成
            String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            int length = 10;
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(charSet.length());
                sb.append(charSet.charAt(index));
            }
            String randomPassword = sb.toString();

            // 権限フラグ
            String role = req.getParameter("role");
            boolean isAdmin = "admin".equalsIgnoreCase(role);

            // 登録処理
            Pair<Boolean, Integer> result = operatorDao.insert(randomPassword, isAdmin);

            if (result.getLeft()) {
                req.setAttribute("message", "発行が完了しました");
                req.setAttribute("operatorId", result.getRight());
                req.setAttribute("operatorPassword", randomPassword);
            } else {
                errors.add("DBへの登録に失敗しました");
                req.setAttribute("errors", errors);
            }

        } catch (Exception e) {
            errors.add("DBへの登録に失敗しました");
            req.setAttribute("errors", errors);
        }

        req.getRequestDispatcher(url).forward(req, res);
    }
}