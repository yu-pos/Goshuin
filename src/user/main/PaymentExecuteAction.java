package user.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class PaymentExecuteAction extends Action{


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//決済処理（ダミー）


		//ローカル変数の宣言 1
		int regdGoshuinId; //購入する御朱印のID
		boolean success = true; //決済処理の成功可否
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ


		//リクエストパラメータ―の取得 2
		regdGoshuinId = Integer.parseInt(req.getParameter("regdGoshuinId"));

		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("regdGoshuinId", regdGoshuinId);

		//フォワード 7
		if (success) {
			req.getRequestDispatcher("GoshuinOrderExecute.action").forward(req, res);
		} else {
			errors.put("1", "決済処理に失敗しました");
			req.getRequestDispatcher("GoshuinChoose.action").forward(req, res);
		}
	}

}
