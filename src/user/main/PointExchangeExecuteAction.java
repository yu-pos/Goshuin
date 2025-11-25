package user.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import dao.UserDao;
import tool.Action;

public class PointExchangeExecuteAction extends Action {



	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//セッションからユーザー情報を取得
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("user");

		//ローカル変数の宣言 1
		int designGroupId = -1;
		int stickerId = -1;

		UserDao userDao = new UserDao();


		Map<String, String> errors = new HashMap<>();
		//リクエストパラメータ―の取得 2
		String type = req.getParameter("type");

		RegdGoshuinBookDesignGroupDao regdDesignGroupDao = new RegdGoshuinBookDesignGroupDao();
		RegdGoshuinBookStickerDao regdStickerDao = new RegdGoshuinBookStickerDao();

		//DBへデータ保存 5
		//選択した方のIDを取得し、データを更新
		if (type.equals("design")) {
			designGroupId = Integer.parseInt(req.getParameter("designGroupId"));

			OwnedGoshuinBookDesignGroupDao ownedDesignGroupDao = new OwnedGoshuinBookDesignGroupDao();

			RegdGoshuinBookDesignGroup regdDesignGroup = regdDesignGroupDao.getById(designGroupId);

			OwnedGoshuinBookDesignGroup ownedDesignGroup = new OwnedGoshuinBookDesignGroup();
			ownedDesignGroup.setGoshuinBookDesignGroup(regdDesignGroup);
			ownedDesignGroup.setUserId(user.getId());

			//登録
			if (!ownedDesignGroupDao.insert(ownedDesignGroup )) {
				errors.put("1", "御朱印帳デザインの購入に失敗しました");
			}

			if (errors.isEmpty()) {
				user.setPoint(user.getPoint() - 5);
				userDao.update(user);
			}

		} else if (type.equals("sticker")) {
			stickerId = Integer.parseInt(req.getParameter("stickerId"));

			OwnedGoshuinBookStickerDao ownedStickerDao = new OwnedGoshuinBookStickerDao();

			RegdGoshuinBookSticker regdSticker = regdStickerDao.getById(stickerId);

			OwnedGoshuinBookSticker ownedSticker = new OwnedGoshuinBookSticker();
			ownedSticker.setGoshuinBookSticker(regdSticker);
			ownedSticker.setUserId(user.getId());

			//登録
			if (!ownedStickerDao.insert(ownedSticker)) {
				errors.put("2", "ステッカーの購入に失敗しました");
			}

			if (errors.isEmpty()) {
				user.setPoint(user.getPoint() - 2);
				userDao.update(user);
			}
		}

		//セッションのユーザー情報を更新
		session.setAttribute("user", user);


		//レスポンス値をセット 6


		//JSPへフォワード 7
		if (errors.isEmpty()) {
			req.getRequestDispatcher("point_exchange_complete.jsp").forward(req, res);
		} else {
			req.setAttribute("user", user);
			req.setAttribute("errors", errors);

			List<RegdGoshuinBookDesignGroup> regdGoshuinBookDesignGroupList = new ArrayList<>();
			regdGoshuinBookDesignGroupList = regdDesignGroupDao.getAll();
			req.setAttribute("regdGoshuinBookDesignGroupList", regdGoshuinBookDesignGroupList);

			List<RegdGoshuinBookSticker> regdGoshuinBookStickerList = new ArrayList<>();
			regdGoshuinBookStickerList = regdStickerDao.getAll();
			req.setAttribute("regdGoshuinBookStickerList", regdGoshuinBookStickerList);

			req.getRequestDispatcher("point_exchange.jsp").forward(req, res);
		}
	}

}
