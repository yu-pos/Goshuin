package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import dao.ShrineAndTempleDao;

public class ReviewInputAction {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // パラメータから神社仏閣IDを取得
            int shrineId = Integer.parseInt(request.getParameter("id"));

            // 神社仏閣情報を取得
            ShrineAndTempleDao shrineDao = new ShrineAndTempleDao();
            ShrineAndTemple shrine = shrineDao.getById(shrineId);

            if (shrine == null) {
                request.setAttribute("error", "指定された神社仏閣は存在しません。");
                return "error.jsp";
            }

            // リクエストにセット
            request.setAttribute("shrineAndTemple", shrine);

            // ユーザー情報も必要なら取得してセット（例: ログイン済みユーザー）
            // User user = (User) request.getSession().getAttribute("loginUser");
            // request.setAttribute("user", user);

            return "review_input.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "口コミ投稿画面の表示に失敗しました。");
            return "error.jsp";


        }
    }
}
