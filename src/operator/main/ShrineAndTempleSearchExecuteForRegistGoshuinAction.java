package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import dao.OperatorDao;
import dao.ShrineAndTempleDao;
import tool.Action;

public class ShrineAndTempleSearchExecuteForRegistGoshuinAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
		OperatorDao operatorDao = new OperatorDao();
		HttpSession session = req.getSession(true);
		session.setAttribute("operator", operatorDao.login(1, "test"));

		Operator operator = (Operator)session.getAttribute("operator");




		//ローカル変数の宣言 1
		String[] selectedTags;
		String searchStr;
		List<Integer> tagIdList = new ArrayList<>();


		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();

		Map<String, String> errors = new HashMap<>();

		//リクエストパラメータ―の取得 2
		selectedTags = req.getParameterValues("tag");
		searchStr = req.getParameter("name");

		//もしどちらも入力されてなかったらエラーを設定し検索画面にフォワード
		if (Objects.isNull(selectedTags) && Objects.isNull(searchStr)) {
			errors.put("1", "タグ・名称のいずれかを入力してください");
			req.getRequestDispatcher("shrine_and_temple_search_for_goshuin_regist.jsp").forward(req, res);
		}

		//DBからデータ取得 3

		//tagIdListにId一覧を登録
		if (Objects.nonNull(selectedTags)) {
		    for (String val : selectedTags) {
		        if (val != null && !val.isEmpty()) {
		            tagIdList.add(Integer.parseInt(val));
		        }
		    }
		}
		

		//ビジネスロジック 4



		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6


		//JSPへフォワード 7
		req.getRequestDispatcher("shrine_and_temple_search_for_goshuin_regist.jsp").forward(req, res);
	}
}
