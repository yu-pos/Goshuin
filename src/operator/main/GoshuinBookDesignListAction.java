package operator.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.RegdGoshuinBookDesignGroup;
import bean.RegdGoshuinBookSticker;
import dao.OperatorDao;
import dao.RegdGoshuinBookDesignGroupDao;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;

public class GoshuinBookDesignListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
		OperatorDao operatorDao = new OperatorDao();
		HttpSession session = req.getSession(true);
		session.setAttribute("operator", operatorDao.login(1, "test"));

		//ローカル変数の宣言 1
		List<RegdGoshuinBookDesignGroup> designGroupList = new ArrayList<>();
		List<RegdGoshuinBookSticker> stickerList = new ArrayList<>();

		RegdGoshuinBookDesignGroupDao designGroupDao = new RegdGoshuinBookDesignGroupDao();
		RegdGoshuinBookStickerDao stickerDao = new RegdGoshuinBookStickerDao();

		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3
		designGroupList = designGroupDao.getAll();
		stickerList = stickerDao.getAll();

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし


		//レスポンス値をセット 6
		req.setAttribute("designGroupList", designGroupList);
		req.setAttribute("stickerList", stickerList);

		//JSPへフォワード 7
		req.getRequestDispatcher("goshuin_book_design_list.jsp").forward(req, res);

	}
}
