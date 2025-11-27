package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.FavoriteShrineAndTemple;
import bean.ShrineAndTemple;
import bean.User;
import dao.FavoriteShrineAndTempleDao;
import dao.ShrineAndTempleDao;
import tool.Action;

public class FavoriteRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		HttpSession session = req.getSession(); // セッション
		User user = (User)session.getAttribute("user");

    	// パラメータ取得
        int shrineAndTempleId = Integer.parseInt(req.getParameter("id"));
        int userId = user.getId();

        // DAOインスタンス生成
        FavoriteShrineAndTempleDao dao = new FavoriteShrineAndTempleDao();
        ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();

        List<ShrineAndTemple> favSATList = new ArrayList<>();

        // 現在のお気に入り一覧を取得
        List<FavoriteShrineAndTemple> favoriteList = dao.searchByUser(userId);

        if (favoriteList.size() >= 3) {
            // すでに3件登録されている場合 → 変更画面に遷移

        	//お気に入りに登録されている神社仏閣の詳細情報を取得
        	for (FavoriteShrineAndTemple favSAT : favoriteList) {
        		favSATList.add(shrineAndTempleDao.getById(favSAT.getShrineAndTempleId()));
        	}



            req.setAttribute("favoriteList", favSATList);
            req.setAttribute("shrineAndTemple", shrineAndTempleDao.getById(shrineAndTempleId));
            req.getRequestDispatcher("favorite_shrine_and_temple_change.jsp").forward(req, res);
        } else {
            // 登録処理を実行
            FavoriteShrineAndTemple favorite = new FavoriteShrineAndTemple();
            favorite.setShrineAndTempleId(shrineAndTempleId);
            favorite.setUserId(userId);

            boolean success = dao.insert(favorite);

            // 登録成功フラグを渡して完了画面へ
            req.setAttribute("favoriteSuccess", success);
            req.getRequestDispatcher("ShrineAndTempleInfo.action").forward(req, res);
        }
    }
}
