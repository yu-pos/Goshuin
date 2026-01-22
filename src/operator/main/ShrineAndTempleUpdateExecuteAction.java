package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.h2.jdbc.JdbcSQLException;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;
import tool.IframeSrcExtractor;
import tool.ImageUtils;

public class ShrineAndTempleUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//ローカル変数の宣言 1
		String imagePath = null;
		ShrineAndTemple shrineAndTemple = new ShrineAndTemple();
		List<ShrineAndTempleTag> tagList = new ArrayList<>();

		Map<Integer, String> tagTypeMap = new HashMap<>();
		Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();

		List<ShrineAndTempleTag> selectedTagList = new ArrayList<>();

		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		Map<String, String> errors = new HashMap<>();


		//リクエストパラメータ―の取得 2
		int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));

		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String[] tagIds = req.getParameterValues("tag");
		String description = req.getParameter("description");
		String areaInfo = req.getParameter("areaInfo");
		String mapLink = req.getParameter("mapLink");


	    Part image = req.getPart("image");
	    boolean isImageUploaded = image != null && image.getSize() > 0;

        for (String tagId : tagIds) {
        	if (!tagId.isEmpty()) {
        		tagList.add(shrineAndTempleTagDao.getById(Integer.parseInt(tagId)));
        	}

        }


        //埋め込みタグからURLを抽出
        String extractedMapLink = IframeSrcExtractor.extractGoogleMapEmbedUrl(mapLink);
        if (extractedMapLink == null) {
        	errors.put("1", "埋め込みリンクの指定が不正です。");
        }

		//DBからデータ取得 3
		shrineAndTemple = shrineAndTempleDao.getById(shrineAndTempleId);
		selectedTagList = shrineAndTemple.getTagList();

		tagList = shrineAndTempleTagDao.getall();

		//ビジネスロジック 4

	    //アップロードされた画像を保存

		if (isImageUploaded && errors.isEmpty()) {
		    imagePath = ImageUtils.saveImage(image, "shrine_and_temple", req);

		    if (imagePath == null) {
		    	errors.put("2", "画像のアップロードに失敗しました。");
		    }
		}

		//タグ種別情報を取得
		for (ShrineAndTempleTag tag : tagList) {
			tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
		    tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
		}


		// 1. selectedTagList から選択済みタグIDのセットを作る
		Set<Integer> selectedIds = new HashSet<>();
		for (ShrineAndTempleTag selected : selectedTagList) {
		    selectedIds.add(selected.getId());
		}

		// 2. tagList のタグが selectedIds に含まれていれば isSelected = true
		for (ShrineAndTempleTag tag : tagList) {
		    if (selectedIds.contains(tag.getId())) {
		        tag.setSelected(true);
		    } else {
		        tag.setSelected(false);
		    }
		}

		//DBへデータ保存 5

	    //御朱印情報を登録
	    if (errors.isEmpty()) {

	    	shrineAndTemple.setName(name);
	    	shrineAndTemple.setAddress(address);
	    	shrineAndTemple.setDescription(description);
	    	shrineAndTemple.setTagList(tagList);
	    	shrineAndTemple.setAreaInfo(areaInfo);
	    	shrineAndTemple.setMapLink(extractedMapLink);

	    	if(isImageUploaded) {
	    		shrineAndTemple.setImagePath(imagePath);
	    	}

	    	try {


		    	if(!shrineAndTempleDao.update(shrineAndTemple)) {
		    		errors.put("3", "神社仏閣情報の更新に失敗しました");
		    	}
	    	} catch (JdbcSQLException e) {
	    		if ("23505".equals(e.getSQLState())) {
		    		errors.put("3", "神社名が重複しています");
	    	    } else {
	    	    	errors.put("3", "神社仏閣情報の登録に失敗しました");;
	    	    }
	    		ImageUtils.deleteImage("shrine_and_temple", shrineAndTemple.getImagePath(), req);
	    	}
	    }

		//レスポンス値をセット 6
		req.setAttribute("shrineAndTemple", shrineAndTemple);
		req.setAttribute("tagsByType", tagsByType);
		req.setAttribute("tagTypeMap", tagTypeMap);

		//JSPへフォワード 7
		if(errors.isEmpty()) {
			req.getRequestDispatcher("shrine_and_temple_update_complete.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("shrine_and_temple_update.jsp").forward(req, res);
		}
	}
}
