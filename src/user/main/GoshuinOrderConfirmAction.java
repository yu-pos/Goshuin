package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RegdGoshuin;
import dao.RegdGoshuinDao;
import tool.Action;

public class GoshuinOrderConfirmAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {



		//ローカル変数の宣言 1
		int regdGoshuinId;
		RegdGoshuin regdGoshuin = new RegdGoshuin();
		int shrineAndTempleId;

		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();

		//リクエストパラメータ―の取得 2
		regdGoshuinId = Integer.parseInt(req.getParameter("regdGoshuinId"));
		shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));


		//DBからデータ取得 3
		regdGoshuin = regdGoshuinDao.getById(regdGoshuinId);

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("regdGoshuin", regdGoshuin);
		req.setAttribute("shrineAndTempleId", shrineAndTempleId);

		//JSPへフォワード 7

		req.getRequestDispatcher("goshuin_order_confirm.jsp").forward(req, res);
	}

}
