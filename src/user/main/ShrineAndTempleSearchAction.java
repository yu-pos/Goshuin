package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleSearchAction extends Action  {

	 public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        // DAOのインスタンス化
	        ShrineAndTempleTagDao tagDao = new ShrineAndTempleTagDao();

	        // 全タグ一覧とタグ種別を取得
	        List<ShrineAndTempleTag> tagList = tagDao.getall();



	        // リクエストスコープにセット
	        req.setAttribute("tagList", tagList);

	        // 表示ページへフォワード
	        req.getRequestDispatcher("shrine_and_temple_search.jsp").forward(req, res);


}

}