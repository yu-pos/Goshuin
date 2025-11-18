package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.OwnedGoshuin;
import bean.RegdGoshuin;
import bean.User;
import dao.OwnedGoshuinDao;
import dao.RegdGoshuinDao;
import tool.Action;

public class GoshuinOrderExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		HttpSession session = req.getSession(); // セッション
		User user = (User)session.getAttribute("user");

		//ローカル変数の宣言 1
		int regdGoshuinId;
		RegdGoshuin regdGoshuin = new RegdGoshuin();
		OwnedGoshuin ownedGoshuin = new OwnedGoshuin();

		boolean success = false; //成功可否

		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();
		OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();

		//リクエストパラメータ―の取得 2
		regdGoshuinId = Integer.parseInt(req.getParameter("regdGoshuinId"));


		//DBからデータ取得 3
		regdGoshuin = regdGoshuinDao.getById(regdGoshuinId);

		//ビジネスロジック 4
		//OwnedGoshuinにデータを登録
		ownedGoshuin.setUserId(user.getId());
		ownedGoshuin.setGoshuinBookId(user.getActiveGoshuinBook().getId());
		ownedGoshuin.setGoshuin(regdGoshuin);

		//DBへデータ保存 5
		success = ownedGoshuinDao.insert(ownedGoshuin);

		//レスポンス値をセット 6
//		req.setAttribute("regdGoshuin", regdGoshuin);
//		req.setAttribute("shrineAndTempleId", shrineAndTempleId);

		//JSPへフォワード 7
		if (success) {
			req.getRequestDispatcher("goshuin_order_complete.jsp").forward(req, res);
		} else {

		}
	}


}