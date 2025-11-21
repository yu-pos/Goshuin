package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import bean.ShrineAndTempleTag;
import dao.OperatorDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleSearchForChangeInfoAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
		OperatorDao operatorDao = new OperatorDao();
		HttpSession session = req.getSession(true);
		session.setAttribute("operator", operatorDao.login(1, "test"));

		Operator operator = (Operator)session.getAttribute("operator");




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

		//JSPへフォワード 7
		req.getRequestDispatcher("shrine_and_temple_search_for_update_info.jsp").forward(req, res);
	}

}
