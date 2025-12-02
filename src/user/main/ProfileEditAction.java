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

public class ProfileEditAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//セッションからユーザー情報を取得
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");

		List<FavoriteShrineAndTemple> favSATList = new ArrayList<>(); //対象ユーザーのお気に入り神社仏閣
		List<ShrineAndTemple> shrineAndTempleList = new ArrayList<>(); //お気に入り神社仏閣の詳細情報

		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		FavoriteShrineAndTempleDao favSATDao = new FavoriteShrineAndTempleDao();


		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3

		favSATList = favSATDao.searchByUser(user.getId());

		//お気に入り神社仏閣の詳細情報を取得
		for (FavoriteShrineAndTemple favSAT : favSATList) {
			shrineAndTempleList.add(shrineAndTempleDao.getById(favSAT.getShrineAndTempleId()));
		}


		//ビジネスロジック 4


		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("shrineAndTempleList", shrineAndTempleList);


		//JSPへフォワード 7
		req.getRequestDispatcher("profile_edit.jsp").forward(req, res);

	}

}
