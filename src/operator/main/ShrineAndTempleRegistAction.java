package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		//タグリスト
		List<ShrineAndTempleTag> tagList = new ArrayList<>();
		Map<Integer, String> tagTypeMap = new HashMap<>();
		Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();

		List<ShrineAndTempleTag> selectedTagList = new ArrayList<>();

		ShrineAndTemple shrineAndTemple = new ShrineAndTemple();

		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		//リクエストパラメータ―の取得 2
		shrineAndTemple = (ShrineAndTemple)req.getAttribute("shrineAndTemple");

		//DBからデータ取得 3
		tagList = shrineAndTempleTagDao.getall();


		//ビジネスロジック 4
		//タグ種別情報を取得
		for (ShrineAndTempleTag tag : tagList) {
			tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
		    tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
		}

		//もし登録に失敗して戻ってきた場合、選択中タグ情報を取得
		if(shrineAndTemple != null) {
			selectedTagList = shrineAndTemple.getTagList();
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
		}

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("tagsByType", tagsByType);
		req.setAttribute("tagTypeMap", tagTypeMap);

		//JSPへフォワード 7
		req.getRequestDispatcher("shrine_and_temple_regist.jsp").forward(req, res);
	}
}
