package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;
import tool.ImageUtils;

public class ShrineAndTempleRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//ローカル変数の宣言 1
		String imagePath = null;
		ShrineAndTemple shrineAndTemple = new ShrineAndTemple();
		List<ShrineAndTempleTag> tagList = new ArrayList<>();

		List<ShrineAndTempleTag> selectedTagList = new ArrayList<>();

		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		Map<String, String> errors = new HashMap<>();


		//リクエストパラメータ―の取得 2

		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String[] tagIds = req.getParameterValues("tag");
		String description = req.getParameter("description");
		String areaInfo = req.getParameter("areaInfo");
		String mapLink = req.getParameter("mapLink");


	    Part image = req.getPart("image");

        for (String tagId : tagIds) {
        	if (!tagId.isEmpty()) {
        		tagList.add(shrineAndTempleTagDao.getById(Integer.parseInt(tagId)));
        	}

        }


		//DBからデータ取得 3

		//ビジネスロジック 4

	    //アップロードされた画像を保存
	    imagePath = ImageUtils.saveImage(image, "shrine_and_temple", req);

	    if (imagePath == null) {
	    	errors.put("1", "画像のアップロードに失敗しました。");
	    }



		//DBへデータ保存 5

	    //御朱印情報を登録
	    if (errors.isEmpty()) {

	    	shrineAndTemple.setName(name);
	    	shrineAndTemple.setAddress(address);
	    	shrineAndTemple.setDescription(description);
	    	shrineAndTemple.setTagList(tagList);
	    	shrineAndTemple.setAreaInfo(areaInfo);
	    	shrineAndTemple.setMapLink(mapLink);

	    	shrineAndTemple.setImagePath(imagePath);

	    	if(!shrineAndTempleDao.insert(shrineAndTemple)) {
	    		errors.put("2", "神社仏閣情報の登録に失敗しました");
	    		ImageUtils.deleteImage("shrine_and_temple", shrineAndTemple.getImagePath(), req);
	    	}
	    }

		//レスポンス値をセット 6
		req.setAttribute("shrineAndTemple", shrineAndTemple);

		//JSPへフォワード 7
		if(errors.isEmpty()) {
			req.getRequestDispatcher("shrine_and_temple_regist_complete.jsp").forward(req, res);
		} else {

			req.setAttribute("errors", errors);
			req.getRequestDispatcher("ShrineAndTempleRegist.action").forward(req, res);
		}
	}

}
