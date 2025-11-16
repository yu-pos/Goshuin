package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
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
		ShrineAndTemple shrineAndTemple = null; //神社仏閣情報


		UserDao userDao = new UserDao();
		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();
		OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();


		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		//なし

		//JSPへフォワード 7
		req.getRequestDispatcher("goshuin_choose.jsp").forward(req, res);
	}

}
