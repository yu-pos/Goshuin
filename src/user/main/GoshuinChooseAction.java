package user.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.OwnedGoshuin;
import bean.RegdGoshuin;
import bean.User;
import dao.OwnedGoshuinDao;
import dao.RegdGoshuinDao;
import dao.ShrineAndTempleDao;
import dao.UserDao;
import tool.Action;

public class GoshuinChooseAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		User user = null; //ログイン中ユーザー
		int shrineAndTempleId; //神社仏閣ID

		List<RegdGoshuin> regdGoshuinList = new ArrayList<>(); //対象の神社仏閣に登録されている御朱印リスト
		List<OwnedGoshuin> ownedGoshuinList = new ArrayList<>();
		Set<Integer> ownedGoshuinIdList = new HashSet<>(); //ログイン中ユーザーの所持御朱印IDリスト

		UserDao userDao = new UserDao();
		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();
		OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();


		LocalDateTime nowDateTime = LocalDateTime.now();
		HttpSession session=req.getSession();

		//リクエストパラメータ―の取得 2
		user = (User)session.getAttribute("user");
		shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));

		System.out.println(user.getTelNumber());

		//DBからデータ取得 3
		regdGoshuinList = regdGoshuinDao.searchByShrineAndTemple(shrineAndTempleId);
		ownedGoshuinList = ownedGoshuinDao.SearchByUser(user.getId());


		//ビジネスロジック 4

		//ownedGoshuinListからユーザーの所持している御朱印ID一覧を抽出
		for (OwnedGoshuin ownedGoshuin : ownedGoshuinList) {
			ownedGoshuinIdList.add(ownedGoshuin.getGoshuin().getId());
		}


		for (int i = 0; i < regdGoshuinList.size(); i++) {
			//ユーザーが所持している御朱印の場合、isOwnedをtrueに
			if (ownedGoshuinIdList.contains(regdGoshuinList.get(i).getId())) {
				regdGoshuinList.get(i).setOwned(true);
			}

			//DBに登録されている販売期間と現在日時から販売期間を生成
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			if (Objects.nonNull(regdGoshuinList.get(i).getSaleStartDate())) {

				//販売開始日を変換
				String startDateStr = nowDateTime.getYear()
									+ "/" + regdGoshuinList.get(i).getSaleStartDate().substring(0, 2)
									+ "/" + regdGoshuinList.get(i).getSaleStartDate().substring(3, 5)
									+ " 00:00:00";
				LocalDateTime startDate = LocalDateTime.parse(startDateStr, dtf);
				System.out.println(startDate);

				String endDateStr = nowDateTime.getYear()
						+ "/" + regdGoshuinList.get(i).getSaleEndDate().substring(0, 2)
						+ "/" + regdGoshuinList.get(i).getSaleEndDate().substring(3, 5)
						+ " 23:59:59";
				LocalDateTime endDate = LocalDateTime.parse(endDateStr, dtf);
				System.out.println(startDate);

				//販売開始日より販売終了日が前だった場合、年の値を増やす
				if (startDate.isBefore(endDate)) {
					endDate = endDate.plusYears(1);
				}


				//現在日が販売期間中だった場合、isAvilableをtrueに
				if( startDate.isAfter(nowDateTime) && endDate.isBefore(nowDateTime) ) {
					regdGoshuinList.get(i).setAvailable(true);
				}
			} else {
				regdGoshuinList.get(i).setAvailable(true);
			}

		}



		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("user", user);
		req.setAttribute("regdGoshuinList", regdGoshuinList);

		//JSPへフォワード 7
		req.getRequestDispatcher("goshuin_choose.jsp").forward(req, res);
	}

}
