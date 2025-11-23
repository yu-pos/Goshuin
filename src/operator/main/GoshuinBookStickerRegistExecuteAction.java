package operator.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.RegdGoshuinBookSticker;
import dao.RegdGoshuinBookStickerDao;
import tool.Action;
import tool.ImageUtils;

public class GoshuinBookStickerRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1

		RegdGoshuinBookStickerDao stickerDao = new RegdGoshuinBookStickerDao();

		Map<String, String> errors = new HashMap<>();


		//リクエストパラメータ―の取得 2
		String name = req.getParameter("name");

	    Part image = req.getPart("image");




		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4

	    //アップロードされた画像を保存
	    String savedFilename = ImageUtils.saveImage(image, "sticker", req);

	    if (savedFilename == null) {
	    	errors.put("1", "画像のアップロードに失敗しました。");
	    }



		//DBへデータ保存 5

	    //御朱印情報を登録
	    if (errors.isEmpty()) {
			RegdGoshuinBookSticker sticker = new RegdGoshuinBookSticker();
			sticker.setName(name);
			sticker.setImagePath(savedFilename);

			if(!stickerDao.insert(sticker)) {
				errors.put("1", "ステッカーの登録に失敗しました。");
			};
	    }

		//レスポンス値をセット 6
		req.setAttribute("name", name);

		//JSPへフォワード 7
		if(errors.isEmpty()) {
			req.getRequestDispatcher("goshuin_book_design_regist_complete.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("goshuin_book_design_regist.jsp?type=sticker").forward(req, res);
		}


	}

}
