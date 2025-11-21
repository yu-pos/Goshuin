package operator.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.Operator;
import bean.RegdGoshuin;
import dao.RegdGoshuinDao;
import dao.ShrineAndTempleDao;
import tool.Action;
import tool.ImageUtils;

@MultipartConfig //ファイルアップロード受付
public class GoshuinRegistExecuteAction extends Action {

	private static boolean validateDate(String input) {

        // "mm-dd" 形式チェック
        if (!input.matches("\\d{2}-\\d{2}")) {
            return false;
        }

        String[] parts = input.split("-");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);

        // 月チェック
        if (month < 1 || month > 12) {
            return false;
        }

        // 月ごとの日数
        int[] daysInMonth = {
            31, // 1月
            28, // 2月（平年として扱う）
            31, // 3月
            30, // 4月
            31, // 5月
            30, // 6月
            31, // 7月
            31, // 8月
            30, // 9月
            31, //10月
            30, //11月
            31  //12月
        };

        int maxDay = daysInMonth[month - 1];

        // 日チェック
        if (day < 1 || day > maxDay) {
            return false;
        }

        return true;
    }

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Operator operator = (Operator)session.getAttribute("operator");


		//ローカル変数の宣言 1
		boolean isLimited = false; //販売期間が限定されているどうか

		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();

		Map<String, String> errors = new HashMap<>();


		//リクエストパラメータ―の取得 2
		int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));
		String description = req.getParameter("description");
		String saleStartDate = req.getParameter("saleStartDate");
		String saleEndDate = req.getParameter("saleEndDate");

		//販売開始日と終了日のどちらかが空欄だった場合や、形式が不正である場合はエラー
		//どちらも設定されていた場合isLimitedをTrueに
		if (saleStartDate.isEmpty() ^ saleEndDate.isEmpty()) {
			errors.put("1", "販売期間を設定する場合は開始日と終了日の両方を指定してください。");
		} else if (!saleStartDate.isEmpty() && !saleEndDate.isEmpty()) {

			if (!validateDate(saleStartDate) || !validateDate(saleEndDate)) {
				errors.put("1", "販売期間が不正です。月-日の形式で入力してください。(例: 01-01)");
			} else {
				isLimited = true;
			}
		}

	    Part image = req.getPart("image");




		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4

	    //アップロードされた画像を保存
	    String savedFilename = ImageUtils.saveImage(image, "goshuin", req);

	    if (savedFilename == null) {
	    	errors.put("1", "画像のアップロードに失敗しました。");
	    }



		//DBへデータ保存 5

	    //御朱印情報を登録
	    if (errors.isEmpty()) {
			RegdGoshuin goshuin = new RegdGoshuin();
			goshuin.setShrineAndTemple(shrineAndTempleDao.getById(shrineAndTempleId));
			goshuin.setDescription(description);

			if (isLimited) {
				goshuin.setSaleStartDate(saleStartDate);
				goshuin.setSaleEndDate(saleEndDate);
			}

			goshuin.setImagePath(savedFilename);

			if(!regdGoshuinDao.insert(goshuin)) {
				errors.put("1", "御朱印情報の登録に失敗しました。");
			};
	    }

		//レスポンス値をセット 6
		req.setAttribute("shrineAndTempleId", shrineAndTempleId);
		req.setAttribute("description", description);
		req.setAttribute("saleStartDate", saleStartDate);
		req.setAttribute("saleEndDate", saleEndDate);

		//JSPへフォワード 7
		if(errors.isEmpty()) {
			req.getRequestDispatcher("goshuin_regist_complete.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("goshuin_regist.jsp").forward(req, res);
		}
	}


}
