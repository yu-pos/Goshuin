package user.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.OwnedGoshuin;
import bean.RegdGoshuin;
import bean.User;
import dao.OwnedGoshuinDao;
import dao.RegdGoshuinDao;
import dao.ShrineAndTempleDao;
import dao.UserDao;
import tool.Action;

public class GoshuinChooseAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		User user = null; //ログイン中ユーザー
		int shrineAndTempleId; //神社仏閣ID

		List<RegdGoshuin> regdGoshuinList = new ArrayList<>(); //対象の神社仏閣に登録されている御朱印リスト
		List<OwnedGoshuin> ownedGoshuinList = new ArrayList<>();
		Set<Integer> ownedGoshuinIdList = new HashSet<>(); //ログイン中ユーザーの所持御朱印IDリスト

		UserDao userDao = new UserDao();
		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();
		OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();

		HttpSession session=req.getSession();

		//リクエストパラメータ―の取得 2
		user = (User)session.getAttribute("user");
		shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));


		//DBからデータ取得 3
		regdGoshuinList = regdGoshuinDao.searchByShrineAndTemple(shrineAndTempleId);
		ownedGoshuinList = ownedGoshuinDao.SearchByUser(user.getId());


		//ビジネスロジック 4

		//ownedGoshuinListからユーザーの所持している御朱印ID一覧を抽出
		for (OwnedGoshuin ownedGoshuin : ownedGoshuinList) {
			ownedGoshuinIdList.add(ownedGoshuin.getGoshuin().getId());
		}

		System.out.println(ownedGoshuinList);
		System.out.println(ownedGoshuinIdList);

		//ユーザーが所持している御朱印の場合、isOwnedをtrueに
		for (int i = 0; i < regdGoshuinList.size(); i++) {
			if (ownedGoshuinIdList.contains(regdGoshuinList.get(i).getId())) {
				regdGoshuinList.get(i).setOwned(true);
			}
			System.out.println(regdGoshuinList.get(i).isOwned());
		}



		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("user", user);
		req.setAttribute("regdGoshuinList", regdGoshuinList);

		//JSPへフォワード 7
		req.getRequestDispatcher("goshuin_choose.jsp").forward(req, res);
	}

}
