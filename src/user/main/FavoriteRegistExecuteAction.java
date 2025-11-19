package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FavoriteShrineAndTemple;
import dao.FavoriteShrineAndTempleDao;
import tool.Action;

public class FavoriteRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // パラメータ取得
        int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));
        int userId = Integer.parseInt(req.getParameter("userId"));

        // DAOインスタンス生成
        FavoriteShrineAndTempleDao dao = new FavoriteShrineAndTempleDao();

        // 現在のお気に入り一覧を取得
        List<FavoriteShrineAndTemple> favoriteList = dao.searchByUser(userId);

        if (favoriteList.size() >= 3) {
            // すでに3件登録されている場合 → 変更画面に遷移
            req.setAttribute("favoriteList", favoriteList);
            req.setAttribute("newShrineAndTempleId", shrineAndTempleId);
            req.getRequestDispatcher("favorite_shrine_temple_change.jsp").forward(req, res);
        } else {
            // 登録処理を実行
            FavoriteShrineAndTemple favorite = new FavoriteShrineAndTemple();
            favorite.setShrineAndTempleId(shrineAndTempleId);
            favorite.setUserId(userId);

            boolean success = dao.insert(favorite);

            // 登録成功フラグを渡して完了画面へ
            req.setAttribute("registSuccess", success);
            req.getRequestDispatcher("favorite_complete.jsp").forward(req, res);
        }
    }
}
