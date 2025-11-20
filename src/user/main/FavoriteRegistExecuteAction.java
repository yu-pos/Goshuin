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
import dao.UserDao;
import tool.Action;

public class FavoriteRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
		UserDao userDao = new UserDao();
		HttpSession session = req.getSession(true);
		session.setAttribute("user", userDao.login("11111111111", "test"));

//		HttpSession session = req.getSession(); // セッション
		User user = (User)session.getAttribute("user");

    	// パラメータ取得
//        int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));
        int shrineAndTempleId = 4; //テスト用。完成したら上と入れ替え
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
            req.setAttribute("newShrineAndTempleId", shrineAndTempleId);
            req.getRequestDispatcher("favorite_shrine_and_temple_change.jsp").forward(req, res);
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
