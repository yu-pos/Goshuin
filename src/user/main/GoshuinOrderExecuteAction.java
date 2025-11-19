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
		Map<String, String> errors = new HashMap<>();

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
		if (!ownedGoshuinDao.insert(ownedGoshuin)) {
			errors.put("1", "御朱印の購入に失敗しました。");
		}

		user.setGoshuinCount(user.getGoshuinCount() + 1); //御朱印カウントを増やす


		System.out.println("DEBUG 登録御朱印数:" + (user.getActiveGoshuinBook().getGoshuinList().size() + 1));


		//もし御朱印帳が埋まった場合、新たな御朱印帳の発行を行う
		if (user.getActiveGoshuinBook().getGoshuinList().size() + 1 >= 30) {
			goshuinBookInsertInfo = goshuinBookDao.insert(user.getId()); //御朱印帳発行

			if (goshuinBookInsertInfo.getLeft()) {
				user.setActiveGoshuinBook(goshuinBookDao.getById(goshuinBookInsertInfo.getRight())); // 使用中御朱印帳を変更
				messages.put("1", "使用中の御朱印帳に登録御朱印が最大に達したため、新たな御朱印帳が発行されました。<br>"
						+ "次回の購入以降、新たな御朱印帳に御朱印が登録されます。");
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

			messages.put("2", "御朱印を規定数集めたため、商品券が発行されました！");
			System.out.println(user.getGoshuinCount());
			//ランクが最大(0)ではなかった場合
			if (user.getRank() > 0) {
				user.setRank(user.getRank() - 1);
				messages.put("2", "御朱印を規定数集めたため、ランクが上がりました！<br>"
						+ "特典として商品券が発行されました。");
			}

			//御朱印カウントをリセット
			user.setGoshuinCount(0);



		}

		//利用者の更新情報をDBに登録し、セッションを更新
		if (!userDao.update(user)) {
			errors.put("2", "利用者情報の更新に失敗しました。");
		}

        session.setAttribute("user", userDao.getById(user.getId()));



		//レスポンス値をセット 6
//		req.setAttribute("regdGoshuin", regdGoshuin);
//		req.setAttribute("shrineAndTempleId", shrineAndTempleId);

		//JSPへフォワード 7
		if (errors.isEmpty()) {
			req.setAttribute("messages", messages);
			req.getRequestDispatcher("goshuin_order_complete.jsp").forward(req, res);
		} else {
//			errors.put("1", "決済処理に失敗しました");
			req.getRequestDispatcher("GoshuinChoose.action").forward(req, res);
		}
	}


}