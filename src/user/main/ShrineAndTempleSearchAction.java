package user.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleSearchAction extends Action  {

	 public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
			List<ShrineAndTempleTag> tagList = new ArrayList<>();
			Map<Integer, String> tagTypeMap = new HashMap<>();
			Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();


			ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();


			//リクエストパラメータ―の取得 2
			//なし

			//DBからデータ取得 3
			tagList = shrineAndTempleTagDao.getall();


			//ビジネスロジック 4

			//タグ種別情報を取得
			for (ShrineAndTempleTag tag : tagList) {
				tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
			    tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
			}

			//DBへデータ保存 5
			//なし

			//レスポンス値をセット 6
			req.setAttribute("tagsByType", tagsByType);
			req.setAttribute("tagTypeMap", tagTypeMap);

	        // 表示ページへフォワード
	        req.getRequestDispatcher("shrine_and_temple_search.jsp").forward(req, res);


}

}