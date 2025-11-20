package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.RegdGoshuinBookDesignGroup;
import bean.RegdGoshuinBookSticker;
import dao.UserDao;

public class PointExchangeExecuteAction {



	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
		UserDao userDao = new UserDao();
		HttpSession session = req.getSession(true);
		session.setAttribute("user", userDao.login("111-1111-1111", "test"));


		//ローカル変数の宣言 1
		List<RegdGoshuinBookDesignGroup> regdGoshuinBookDesignGroupList = new ArrayList<>();
		List<RegdGoshuinBookSticker> regdGoshuinBookList = new ArrayList<>();
		List<RegdGoshuinBookDesignGroup> regdBookDesignGroupList = new ArrayList<>();
		List<Regdownedgoshuin> regdownedgoshuinList = new ArrayList<>();

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
		req.getRequestDispatcher("qr_code_scan.jsp").forward(req, res);
	}

}
