package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleSearchExecuteForUpdateInfoAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {



		HttpSession session = req.getSession();
		Operator operator = (Operator)session.getAttribute("operator");




		//ローカル変数の宣言 1

		String searchStr;
		List<Integer> tagIdList = new ArrayList<>();
		List<ShrineAndTemple> results = new ArrayList<>();

		List<ShrineAndTempleTag> tagList = new ArrayList<>();
		Map<Integer, String> tagTypeMap = new HashMap<>();
		Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();


		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		Map<String, String> errors = new HashMap<>();

		//リクエストパラメータ―の取得 2



		//入力された値を取得
		String[] selectedTags = req.getParameterValues("tag");
		searchStr = req.getParameter("name");
		if (selectedTags != null) {
		    for (String val : selectedTags) {
		        if (val != null && !val.isEmpty()) {
		            tagIdList.add(Integer.parseInt(val));
		        }
		    }
		}
      if (tagIdList.isEmpty() && (searchStr == null || searchStr.isEmpty())) {
    errors.put("1", "タグ・名称のいずれかを入力してください");
}


		System.out.println("[DEBUG] searchStr = " + searchStr);
//		System.out.println("[DEBUG] selectedTags[0] = " + selectedTags[0]);


		//tagIdListにId一覧を登録
















		//もしどちらも入力されてなかったらエラーを設定し検索画面にフォワード




		//DBからデータ取得 3
		tagList = shrineAndTempleTagDao.getall();


		if (!tagIdList.isEmpty() && !searchStr.equals("")) {
			results = shrineAndTempleDao.searchByNameAndTag(searchStr, tagIdList);
			System.out.println("[DEBUG] searchByNameAndTag実行");
		} else if(!tagIdList.isEmpty()) {
			results = shrineAndTempleDao.searchByTag(tagIdList);
			System.out.println("[DEBUG] searchByTag実行");
		} else if(!searchStr.equals("")) {
			results = shrineAndTempleDao.searchByName(searchStr);
			System.out.println("[DEBUG] searchByName実行");
		}

		//ビジネスロジック 4
		//タグ種別情報を取得
		for (ShrineAndTempleTag tag : tagList) {

			if (tagIdList.contains(tag.getId())) {
				tag.setSelected(true);
			}

			tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
		    tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);


		}

		// 結果ごとのタグリストをセット
		for (ShrineAndTemple result : results) {

		    List<ShrineAndTempleTag> tagsForResult = shrineAndTempleTagDao.searchByShrineAndTemple(result.getId());
		    Map<Integer, List<ShrineAndTempleTag>> tagsByTypeForResult = new HashMap<>();
		    for (ShrineAndTempleTag tag : tagsForResult) {
		        tagsByTypeForResult.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
		    }
		    result.setTagsByType(tagsByTypeForResult); // ShrineAndTemple にフィールド追加
		}


		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("results", results);
		req.setAttribute("selectedTags", selectedTags);
		req.setAttribute("searchStr", searchStr);

		req.setAttribute("tagsByType", tagsByType);
		req.setAttribute("tagTypeMap", tagTypeMap);

		//JSPへフォワード 7
		if (errors.isEmpty()) {
			req.getRequestDispatcher("shrine_and_temple_search_results_for_update_info.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("shrine_and_temple_search_for_update_info.jsp").forward(req, res);
		}
	}

}
