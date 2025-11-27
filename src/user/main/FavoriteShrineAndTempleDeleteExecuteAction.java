package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.FavoriteShrineAndTemple;
import bean.User;
import dao.FavoriteShrineAndTempleDao;
import tool.Action;

public class FavoriteShrineAndTempleDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
        // パラメータ取得（削除対象の神社仏閣IDとユーザーID）
        int oldFavoriteShrineId = Integer.parseInt(req.getParameter("oldFavoriteShrineId"));
        int userId = user.getId();

        // Beanにセット
        FavoriteShrineAndTemple favorite = new FavoriteShrineAndTemple();
        favorite.setShrineAndTempleId(oldFavoriteShrineId);
        favorite.setUserId(userId);

        // DAO呼び出し
        FavoriteShrineAndTempleDao dao = new FavoriteShrineAndTempleDao();
        boolean deleteSuccess = dao.delete(favorite);

        // 削除成功時 → 登録処理へリダイレクト（FavoriteRegistExecuteActionを起動）
        if (deleteSuccess) {
            // 新しく登録したい神社仏閣IDを取得してリダイレクト
            String newId = req.getParameter("newFavoriteShrineId");
            res.sendRedirect("FavoriteRegistExecute.action?id=" + newId);
        } else {
            // 削除失敗時 → エラーページへ
            req.setAttribute("deleteSuccess", false);
            req.getRequestDispatcher("favorite_error.jsp").forward(req, res);
        }
    }
}
