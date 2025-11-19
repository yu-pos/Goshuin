package user.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.tuple.Pair;

import bean.OwnedGoshuin;
import bean.Rank;
import bean.RegdGoshuin;
import bean.User;
import bean.Voucher;
import dao.GoshuinBookDao;
import dao.OwnedGoshuinDao;
import dao.RankDao;
import dao.RegdGoshuinDao;
import dao.UserDao;
import dao.VoucherDao;
import tool.Action;

public class GoshuinOrderExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		HttpSession session = req.getSession(); // セッション
		User user = (User)session.getAttribute("user");

		System.out.println( user.getRank());
		//ローカル変数の宣言 1
		int regdGoshuinId;
		RegdGoshuin regdGoshuin = new RegdGoshuin();
		OwnedGoshuin ownedGoshuin = new OwnedGoshuin();
		Rank rank = new Rank();

		Pair<Boolean, Integer> goshuinBookInsertInfo;

		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();
		OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();
		RankDao rankDao = new RankDao();
		UserDao userDao = new UserDao();
		VoucherDao voucherDao = new VoucherDao();
		GoshuinBookDao goshuinBookDao = new GoshuinBookDao();

		Map<String, String> messages = new HashMap<>();

		//リクエストパラメータ―の取得 2
		regdGoshuinId = Integer.parseInt(req.getParameter("regdGoshuinId"));


		//DBからデータ取得 3
		regdGoshuin = regdGoshuinDao.getById(regdGoshuinId); //選択された御朱印の情報

		rank = rankDao.getById(user.getRank());


		//ビジネスロジック 4
		//OwnedGoshuinにデータを登録
		ownedGoshuin.setUserId(user.getId());
		ownedGoshuin.setGoshuinBookId(user.getActiveGoshuinBook().getId());
		ownedGoshuin.setGoshuin(regdGoshuin);

		//DBへデータ保存 5
		ownedGoshuinDao.insert(ownedGoshuin);

		user.setGoshuinCount(user.getGoshuinCount() + 1); //御朱印カウントを増やす

		//御朱印帳発行処理
		if (user.getActiveGoshuinBook().getGoshuinList().size() >= 30) {

			goshuinBookInsertInfo = goshuinBookDao.insert(user.getId()); //御朱印帳発行

			if (goshuinBookInsertInfo.getLeft()) {
				user.setActiveGoshuinBook(goshuinBookDao.getById(goshuinBookInsertInfo.getRight())); // 使用中御朱印帳を変更
				messages.put("1", "御朱印帳が埋まったため、新たな御朱印帳が発行されました。");
			}


		}

		System.out.println(rank.getRankUpQuantity());
		//ランクアップ・商品券付与処理
		if (user.getGoshuinCount() >= rank.getRankUpQuantity()) {

			//商品券付与（仮置き）
			Voucher voucher = new Voucher();
			voucher.setUserId(user.getId());
			voucher.setDescription("付与テスト用商品券");
			voucher.setImagePath("voucher_test.png");

			voucherDao.insert(voucher);

			messages.put("2", "御朱印を規定数集めたため、商品券が発行されました");
			System.out.println(user.getGoshuinCount());
			//ランクが最大(0)ではなかった場合
			if (user.getRank() > 0) {
				user.setRank(user.getRank() - 1);
				messages.put("2", "御朱印を規定数集めたため、ランクが上がりました。特典として商品券が発行されました");
			}

			//御朱印カウントをリセット
			user.setGoshuinCount(0);



		}

		userDao.update(user);



		//レスポンス値をセット 6
//		req.setAttribute("regdGoshuin", regdGoshuin);
//		req.setAttribute("shrineAndTempleId", shrineAndTempleId);

		//JSPへフォワード 7
		if (true) {
			req.setAttribute("messages", messages);
			req.getRequestDispatcher("goshuin_order_complete.jsp").forward(req, res);
		} else {
//			errors.put("1", "決済処理に失敗しました");
			req.getRequestDispatcher("GoshuinChoose.action").forward(req, res);
		}
	}


}