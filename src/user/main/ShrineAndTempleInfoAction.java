package user.main;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.FavoriteShrineAndTemple;
import bean.Review;
import bean.ShrineAndTemple;
import bean.User;
import dao.FavoriteShrineAndTempleDao;
import dao.ReviewDao;
import dao.ShrineAndTempleDao;
import tool.Action;

public  class ShrineAndTempleInfoAction extends Action  {

	public  void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	//セッションからデータを取得
	HttpSession session = req.getSession();
	User user = (User)session.getAttribute("user");

	// リクエストパラメータから神社仏閣IDを取得
    int shrineAndTempleId = Integer.parseInt(req.getParameter("id"));


    // DAOのインスタンス化
	ShrineAndTempleDao shrineAndTempleDao=new ShrineAndTempleDao();
    ReviewDao reviewDao = new ReviewDao();
    FavoriteShrineAndTempleDao favoriteDao = new FavoriteShrineAndTempleDao();


    // 神社仏閣情報を取得
    ShrineAndTemple shrineAndTemple = shrineAndTempleDao.getById(shrineAndTempleId);

    // 口コミ一覧を取得
    List<Review> reviewList = reviewDao.searchByShrineAndTempleId(shrineAndTempleId);

    //お気に入り登録済みか確認
    List<FavoriteShrineAndTemple> favoriteList = new ArrayList<>();
    favoriteList = favoriteDao.searchByUser(user.getId());
    boolean isFavorited = false;

    for (FavoriteShrineAndTemple favorite : favoriteList) {
    	if (favorite.getShrineAndTempleId() == shrineAndTempleId) {
    		isFavorited = true;
    		break;
    	}
    }
    // リクエストスコープにセット
    req.setAttribute("shrineAndTemple", shrineAndTemple);
    req.setAttribute("reviewList", reviewList);
    req.setAttribute("isFavorited", isFavorited);

    // JSPへフォワード
    req.getRequestDispatcher("shrine_and_temple_info.jsp").forward(req, res);

	}
}