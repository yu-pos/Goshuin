package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.OwnedGoshuinBookDesignGroup;
import bean.OwnedGoshuinBookSticker;
import bean.RegdGoshuinBookDesignGroup;
import bean.RegdGoshuinBookSticker;
import bean.User;
import dao.OwnedGoshuinBookDesignGroupDao;
import dao.OwnedGoshuinBookStickerDao;
import dao.RegdGoshuinBookDesignGroupDao;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;

public class PointExchangeAction extends Action{


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(); // セッション
		User user = (User)session.getAttribute("user");


		//ローカル変数の宣言 1
		List<RegdGoshuinBookDesignGroup> regdGoshuinBookDesignGroupList = new ArrayList<>();
		List<RegdGoshuinBookSticker> regdGoshuinBookStickerList = new ArrayList<>();
		List<OwnedGoshuinBookDesignGroup> ownedGoshuinBookDesignGroupList = new ArrayList<>();
		List<OwnedGoshuinBookSticker> ownedGoshuinBookStickerList = new ArrayList<>();

		//所持デザイン・ステッカーのIDリスト
		List<Integer> ownedGoshuinBookDesignGroupIdList = new ArrayList<>();
		List<Integer> ownedGoshuinBookStickerIdList = new ArrayList<>();

		RegdGoshuinBookDesignGroupDao regdGoshuinBookDesignGroupDao = new RegdGoshuinBookDesignGroupDao();
		RegdGoshuinBookStickerDao regdGoshuinBookStickerDao = new RegdGoshuinBookStickerDao();

		OwnedGoshuinBookDesignGroupDao ownedGoshuinBookDesignGroupDao = new OwnedGoshuinBookDesignGroupDao();
		OwnedGoshuinBookStickerDao ownedGoshuinBookStickerDao = new OwnedGoshuinBookStickerDao();

		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3
		regdGoshuinBookDesignGroupList = regdGoshuinBookDesignGroupDao.getAll();
		regdGoshuinBookStickerList = regdGoshuinBookStickerDao.getAll();

		ownedGoshuinBookDesignGroupList = ownedGoshuinBookDesignGroupDao.getByUser(user.getId());
		ownedGoshuinBookStickerList = ownedGoshuinBookStickerDao.searchByUser(user.getId());



		//ビジネスロジック 4
		//ownedGoshuinBookDesignGroupListからユーザーの所持している御朱印デザイングループID一覧を抽出
		for (OwnedGoshuinBookDesignGroup ownedGoshuinBookDesignGroup : ownedGoshuinBookDesignGroupList) {
			ownedGoshuinBookDesignGroupIdList.add(ownedGoshuinBookDesignGroup.getGoshuinBookDesignGroup().getId());
		}

		//ownedGoshuinBookStickerListからユーザーの所持している御朱印デザイングループID一覧を抽出
		for (OwnedGoshuinBookSticker ownedGoshuinBookSticker : ownedGoshuinBookStickerList) {
			ownedGoshuinBookStickerIdList.add(ownedGoshuinBookSticker.getGoshuinBookSticker().getId());
		}

		//購入可能判定処理
		for (int i = 0; i < regdGoshuinBookDesignGroupList.size(); i++) {
			//ユーザーが所持している御朱印の場合、isOwnedをtrueに
			if (ownedGoshuinBookDesignGroupIdList.contains(regdGoshuinBookDesignGroupList.get(i).getId())) {
				regdGoshuinBookDesignGroupList.get(i).setOwned(true);
			}

		}

		for (int i = 0; i < regdGoshuinBookStickerList.size(); i++) {
			//ユーザーが所持している御朱印の場合、isOwnedをtrueに
			if (ownedGoshuinBookStickerIdList.contains(regdGoshuinBookStickerList.get(i).getId())) {
				regdGoshuinBookStickerList.get(i).setOwned(true);
			}
		}

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("point", user.getPoint());
		req.setAttribute("regdGoshuinBookDesignGroupList", regdGoshuinBookDesignGroupList);
		req.setAttribute("regdGoshuinBookStickerList", regdGoshuinBookStickerList);

		//JSPへフォワード 7
		req.getRequestDispatcher("point_exchange.jsp").forward(req, res);
	}

}
