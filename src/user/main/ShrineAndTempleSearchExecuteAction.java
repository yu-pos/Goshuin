package user.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleSearchExecuteAction extends Action {



	        @Override
	        public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	            // 選択されたタグIDを取得（例：複数選択）
	            String[] tagIds = req.getParameterValues("tag");
	            //入力されたテキストを取得
	            String searchStr = req.getParameter("name");
	            if (searchStr == null) {
	                searchStr = "";
	            }

	            List<ShrineAndTempleTag> tagList = new ArrayList<>();
	    		Map<Integer, String> tagTypeMap = new HashMap<>();
	    		Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();

	            ShrineAndTempleTagDao tagDao = new ShrineAndTempleTagDao();


	            Map<String, String> errors = new HashMap<>();

	            // タグIDを整数リストに変換
	            List<Integer> tagIdList = new ArrayList<>();
	            if(tagIds != null) {

		            for (String tagId : tagIds) {
		            	if(!tagId.isEmpty()) {
		            		tagIdList.add(Integer.parseInt(tagId));
		            	}
		            }
	            }



	            //もしどちらも入力されてなかったらエラーを設定し検索画面にフォワード
	    		if (tagIdList.isEmpty() && searchStr.isEmpty()) {
	    			errors.put("1", "タグ・名称のいずれかを入力してください");
	    		}


	            //タグ種別情報を取得
	    		for (ShrineAndTempleTag tag : tagList) {

	    			if (tagIdList.contains(tag.getId())) {
	    				tag.setSelected(true);
	    			}

	    			tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
	    		    tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);


	    		}

	            // 神社仏閣情報の取得
	            ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();

	            List<ShrineAndTemple> shrineAndTempleList = new ArrayList<>();

	            if(!tagIdList.isEmpty()) {
	    			shrineAndTempleList = shrineAndTempleDao.searchByTag(tagIdList);
	    			System.out.println("[DEBUG] searchByTag実行");
	    		} else if(!searchStr.equals("")) {
	    			shrineAndTempleList = shrineAndTempleDao.searchByName(searchStr);
	    			System.out.println("[DEBUG] searchByName実行");
	    		}

	            // 結果ごとのタグリストをセット
	    		for (ShrineAndTemple result : shrineAndTempleList) {

	    		    List<ShrineAndTempleTag> tagsForResult = tagDao.searchByShrineAndTemple(result.getId());
	    		    Map<Integer, List<ShrineAndTempleTag>> tagsByTypeForResult = new HashMap<>();
	    		    for (ShrineAndTempleTag tag : tagsForResult) {
	    		        tagsByTypeForResult.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
	    		    }
	    		    result.setTagsByType(tagsByTypeForResult); // ShrineAndTemple にフィールド追加
	    		}


	            // リクエストスコープにセット
	            req.setAttribute("shrineAndTempleList", shrineAndTempleList);
	            req.setAttribute("tagsByType", tagsByType);
	    		req.setAttribute("tagTypeMap", tagTypeMap);
	    		req.setAttribute("name", searchStr);

	            // 表示ページへフォワード
	    		if(errors.isEmpty()) {
	    			req.getRequestDispatcher("shrine_and_temple_search_results.jsp").forward(req, res);
	    		} else {
	    			req.setAttribute("errors", errors);
	    			req.getRequestDispatcher("ShrineAndTempleSearch.action").forward(req, res);
	    		}



	        }




}


