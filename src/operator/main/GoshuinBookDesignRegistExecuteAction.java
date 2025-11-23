package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.tuple.Pair;

import bean.RegdGoshuinBookDesign;
import bean.RegdGoshuinBookDesignGroup;
import dao.RegdGoshuinBookDesignDao;
import dao.RegdGoshuinBookDesignGroupDao;
import tool.Action;
import tool.ImageUtils;

public class GoshuinBookDesignRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		RegdGoshuinBookDesignGroup designGroup = new RegdGoshuinBookDesignGroup();
		List<RegdGoshuinBookDesign> designList = new ArrayList<>();


		RegdGoshuinBookDesignGroupDao designGroupDao = new RegdGoshuinBookDesignGroupDao();
		RegdGoshuinBookDesignDao designDao = new RegdGoshuinBookDesignDao();

		Map<String, String> errors = new HashMap<>();


		//リクエストパラメータ―の取得 2
		String groupName = req.getParameter("groupName");
		int amount = Integer.parseInt(req.getParameter("amount"));


		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4


		//DBへデータ保存 5

		//デザイングループ情報を登録
		designGroup.setName(groupName);
		Pair<Boolean, Integer> pair = designGroupDao.insert(designGroup);
		if(!pair.getLeft()) {
			errors.put("1", "御朱印帳デザイングループ情報の登録に失敗しました。初めからやりなおしてください。");
		}

		if(errors.isEmpty()) {
			//もしデザイングループ情報の登録に成功していた場合、御朱印帳デザインを登録

			//登録する御朱印帳デザインのリストを作成
			for (int i = 1; i <= amount; i++ ) {
				System.out.println("[DEBUG] i = " + i);
				RegdGoshuinBookDesign design = new RegdGoshuinBookDesign();

				String color = req.getParameter("color" + i);

				Part image = req.getPart("image" + i);
				//アップロードされた画像を保存
			    String savedFilename = ImageUtils.saveImage(image, "goshuin_book_design", req);

			    //savedFilenameの取得に失敗した場合
			    if (savedFilename == null) {
			    	errors.put("2", "画像のアップロードに失敗しました。初めからやりなおしてください。");
			    	//登録したデザイングループ情報を削除しbreak
			    	designGroupDao.delete(pair.getRight());
			    	break;
			    }

			    design.setGoshuinBookDesignGroupId(pair.getRight());
			    design.setName(color);
			    design.setImagePath(savedFilename);

			    designList.add(design);
			}

			if(!designDao.insertList(designList)) {
				errors.put("3", "御朱印帳デザインの登録に失敗しました。初めからやりなおしてください。");
			}
		}

		//レスポンス値をセット 6
		req.setAttribute("groupName", groupName);
		req.setAttribute("amount", amount);

		//JSPへフォワード 7
		if(errors.isEmpty()) {
			req.getRequestDispatcher("goshuin_book_design_regist_complete.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("GoshuinBookDesignRegist.action?type=design").forward(req, res);
		}


	}

}
