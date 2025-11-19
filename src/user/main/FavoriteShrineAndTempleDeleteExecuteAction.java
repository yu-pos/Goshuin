package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FavoriteShrineAndTemple;
import dao.FavoriteShrineAndTempleDao;
import tool.Action;

public class FavoriteShrineAndTempleDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // パラメータ取得（削除対象の神社仏閣IDとユーザーID）
        int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));
        int userId = Integer.parseInt(req.getParameter("userId"));

        // Beanにセット
        FavoriteShrineAndTemple favorite = new FavoriteShrineAndTemple();
        favorite.setShrineAndTempleId(shrineAndTempleId);
        favorite.setUserId(userId);

        // DAO呼び出し
        FavoriteShrineAndTempleDao dao = new FavoriteShrineAndTempleDao();
        boolean deleteSuccess = dao.delete(favorite);

        // 削除成功時 → 登録処理へリダイレクト（FavoriteRegistExecuteActionを起動）
        if (deleteSuccess) {
            // 新しく登録したい神社仏閣IDを取得してリダイレクト
            String newId = req.getParameter("newShrineAndTempleId");
            res.sendRedirect("FavoriteRegistExecute.action?shrineAndTempleId=" + newId + "&userId=" + userId);
        } else {
            // 削除失敗時 → エラーページへ
            req.setAttribute("deleteSuccess", false);
            req.getRequestDispatcher("favorite_error.jsp").forward(req, res);
        }
    }
}
