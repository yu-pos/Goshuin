package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RegdGoshuinBookDesignGroup;
import bean.RegdGoshuinBookSticker;
import dao.RegdGoshuinBookDesignGroupDao;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;

public class PointExchangeConfirmAction extends Action {


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//ローカル変数の宣言
		RegdGoshuinBookDesignGroup designGroup = new RegdGoshuinBookDesignGroup();
		RegdGoshuinBookSticker sticker = new RegdGoshuinBookSticker();

		int designGroupId = -1;
		int stickerId = -1;

		RegdGoshuinBookDesignGroupDao designGroupDao = new RegdGoshuinBookDesignGroupDao();
		RegdGoshuinBookStickerDao stickerDao = new RegdGoshuinBookStickerDao();

		//リクエストパラメータ―の取得 2
		//DBからデータ取得 3
		String type = req.getParameter("type");

		if(type.equals("design")) {
			designGroupId = Integer.parseInt(req.getParameter("designGroupId"));
			designGroup = designGroupDao.getById(designGroupId);
		} else if (type.equals("sticker")) {
			stickerId = Integer.parseInt(req.getParameter("stickerId"));
			sticker = stickerDao.getById(stickerId);
		}

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("type", type);
		req.setAttribute("designGroup", designGroup);
		req.setAttribute("sticker", sticker);

		//JSPへフォワード 7
		req.getRequestDispatcher("point_exchange_confirm.jsp").forward(req, res);
	}

}
