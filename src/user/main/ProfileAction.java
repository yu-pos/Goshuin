package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.FavoriteShrineAndTemple;
import bean.Rank;
import bean.ShrineAndTemple;
import bean.User;
import dao.FavoriteShrineAndTempleDao;
import dao.GoshuinBookDao;
import dao.RankDao;
import dao.ShrineAndTempleDao;
import dao.UserDao;
import tool.Action;

public class ProfileAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//セッションからユーザー情報を取得
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");

		//ローカル変数の宣言 1
		User selectedUser = new User();  //プロフィールを表示するユーザー
		Rank rank = new Rank(); //対象ユーザーのランク情報
		List<FavoriteShrineAndTemple> favSATList = new ArrayList<>(); //対象ユーザーのお気に入り神社仏閣
		List<ShrineAndTemple> shrineAndTempleList = new ArrayList<>(); //お気に入り神社仏閣の詳細情報

		UserDao userDao = new UserDao();
		RankDao rankDao = new RankDao();
		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		FavoriteShrineAndTempleDao favSATDao = new FavoriteShrineAndTempleDao();
		GoshuinBookDao goshuinBookDao = new GoshuinBookDao();


		//リクエストパラメータ―の取得 2
		String userIdStr = req.getParameter("userId");

		//DBからデータ取得 3

		//もしuserIdが渡されていない場合（フッターから遷移した場合）、ログイン中ユーザーをuserIdStrに代入
		if (userIdStr == null) {
			userIdStr = Integer.toString(user.getId());
		}

		selectedUser = userDao.getById(Integer.parseInt(userIdStr));


		rank = rankDao.getById(selectedUser.getRank());
		favSATList = favSATDao.searchByUser(selectedUser.getId());

		//お気に入り神社仏閣の詳細情報を取得
		for (FavoriteShrineAndTemple favSAT : favSATList) {
			shrineAndTempleList.add(shrineAndTempleDao.getById(favSAT.getShrineAndTempleId()));
		}


		//ビジネスロジック 4


		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("selectedUser", selectedUser);
		req.setAttribute("rank", rank);
		req.setAttribute("shrineAndTempleList", shrineAndTempleList);


		//JSPへフォワード 7
		req.getRequestDispatcher("profile.jsp").forward(req, res);

	}

}
