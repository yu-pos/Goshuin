package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class GoshuinBookDesignRegistDetailAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		//なし

		//リクエストパラメータ―の取得 2

		String groupName = req.getParameter("groupName");
		int amount = Integer.parseInt(req.getParameter("amount"));

		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("groupName", groupName);
		req.setAttribute("amount", amount);

		//JSPへフォワード 7s
		req.getRequestDispatcher("goshuin_book_design_regist_detail.jsp").forward(req, res);

	}

}
