package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import tool.Action;

public class GoshuinRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Operator operator = (Operator)session.getAttribute("operator");

		//ローカル変数の宣言 1
		int shrineAndTempleId;


		//リクエストパラメータ―の取得 2
		shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));

		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("shrineAndTempleId", shrineAndTempleId);

		//JSPへフォワード 7
		req.getRequestDispatcher("goshuin_regist.jsp").forward(req, res);
	}


}
