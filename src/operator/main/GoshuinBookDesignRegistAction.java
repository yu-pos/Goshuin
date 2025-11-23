package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class GoshuinBookDesignRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		//なし
		
		//リクエストパラメータ―の取得 2
		//表紙とステッカーどちらを新規登録するか
		String type = req.getParameter("type");

		//DBからデータ取得 3

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし


		//レスポンス値をセット 6
		req.setAttribute("type", type);

		//JSPへフォワード 7
		if(type.equals("design") || type.equals("sticker")) {
			req.getRequestDispatcher("goshuin_book_design_regist.jsp").forward(req, res);
		}

	}

}
