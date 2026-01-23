package operator.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegdGoshuinBookDesignGroupDao;
import tool.Action;

public class GoshuinBookDesignRegistDetailAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		RegdGoshuinBookDesignGroupDao groupDao = new RegdGoshuinBookDesignGroupDao();

		Map<String, String> errors = new HashMap<>();

		//リクエストパラメータ―の取得 2

		String groupName = req.getParameter("groupName");
		int amount = Integer.parseInt(req.getParameter("amount"));


		//DBからデータ取得 3
		if (groupDao.getByName(groupName) != null) {
			errors.put("0", "デザイン名が重複しています");
		}

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("groupName", groupName);
		req.setAttribute("amount", amount);

		//JSPへフォワード 7
		if (errors.isEmpty()) {
			req.getRequestDispatcher("goshuin_book_design_regist_detail.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("GoshuinBookDesignRegist.action?type=design").forward(req, res);
		}

	}

}
