package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FavoriteShrineAndTemple;
import bean.ShrineAndTemple;
import bean.User;
import dao.FavoriteShrineAndTempleDao;
import dao.ShrineAndTempleDao;
import tool.Action;

public class FavoriteRegistExecuteAction extends Action{


	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	 User user = (User) req.getSession().getAttribute("user");
     int userId = user.getId();

     // 登録対象の神社仏閣IDを取得（パラメータが存在する場合のみ）
     String shrineIdParam = req.getParameter("shrineId");
     if (shrineIdParam != null && !shrineIdParam.isEmpty()) {
         int shrineId = Integer.parseInt(shrineIdParam);

         // DAO初期化
         FavoriteShrineAndTempleDao favoriteDao = new FavoriteShrineAndTempleDao();
         List<FavoriteShrineAndTemple> currentFavorites = favoriteDao.searchByUser(userId);

         // 上限チェックと重複チェック
         boolean alreadyRegistered = false;
         for (FavoriteShrineAndTemple f : currentFavorites) {
             if (f.getShrineAndTempleId() == shrineId) {
                 alreadyRegistered = true;
                 break;
             }
         }

         if (currentFavorites.size() >= 3) {
             req.setAttribute("error", "お気に入りは最大3件までです。");
         } else if (alreadyRegistered) {
             req.setAttribute("error", "すでにお気に入りに登録されています。");
         } else {
        	   // 登録処理
             FavoriteShrineAndTemple favorite = new FavoriteShrineAndTemple();
             favorite.setUserId(userId);
             favorite.setShrineAndTempleId(shrineId);
             boolean success = favoriteDao.insert(favorite);
             if (success) {
                 req.setAttribute("message", "お気に入りに登録しました。");
             } else {
                 req.setAttribute("error", "登録に失敗しました。");
             }
         }
     }

     // 登録後または初回表示時のお気に入り一覧取得
     FavoriteShrineAndTempleDao favoriteDao = new FavoriteShrineAndTempleDao();
     List<FavoriteShrineAndTemple> favoriteList = favoriteDao.searchByUser(userId);

     // 神社仏閣情報取得
     ShrineAndTempleDao shrineDao = new ShrineAndTempleDao();
     List<ShrineAndTemple> shrineList = new ArrayList<>();
     for (FavoriteShrineAndTemple favorite : favoriteList) {
         ShrineAndTemple shrine = shrineDao.getById(favorite.getShrineAndTempleId());
         if (shrine != null) {
             shrineList.add(shrine);

             // スコープにセット
             req.setAttribute("shrineList", shrineList);

             // 表示ページへフォワード
             req.getRequestDispatcher("favorite_list.jsp").forward(req, res);

         }
     }


//登録数が三個未満の場合登録ができる









	    // リクエストスコープにセット

	}
}
