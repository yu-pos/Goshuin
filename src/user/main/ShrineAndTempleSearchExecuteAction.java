package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import dao.ShrineAndTempleDao;
import tool.Action;

public class ShrineAndTempleSearchExecuteAction extends Action {



	        @Override
	        public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	            // 選択されたタグIDを取得（例：複数選択）
	            String[] tagIds = req.getParameterValues("tagId");

	            // タグが選択されていない場合の処理
	            if (tagIds == null || tagIds.length == 0) {
	                req.setAttribute("error", "タグを1つ以上選択してください。");
	                req.getRequestDispatcher("tag_search.jsp").forward(req, res);
	                return;
	            }

	            // タグIDを整数リストに変換
	            List<Integer> tagIdList = new ArrayList<>();
	            for (String tagId : tagIds) {
	                tagIdList.add(Integer.parseInt(tagId));
	            }

	            // 神社仏閣情報の取得
	            ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
	            List<ShrineAndTemple> shrineAndTempleList = shrineAndTempleDao.searchByTag(tagIdList);

	            // 検索結果が空の場合の処理
	            if (shrineAndTempleList == null || shrineAndTempleList.isEmpty()) {
	                req.setAttribute("message", "検索結果が存在しませんでした。");
	            }

	            // リクエストスコープにセット
	            req.setAttribute("shrineAndTempleList", shrineAndTempleList);

	            // 表示ページへフォワード
	            req.getRequestDispatcher("shrine_list.jsp").forward(req, res);
	        }




}
	     //   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	            // 選択されたタグIDを取得（例：複数選択）
	      //      String[] tagIds = req.getParameterValues("tagId");

	            // タグが選択されていない場合の処理
	      //      if (tagIds == null || tagIds.length == 0) {
	        //        req.setAttribute("error", "タグを1つ以上選択してください。");
	    //            req.getRequestDispatcher("tag_search.jsp").forward(req, res); // エラー表示用ページへ
	        //        return;
	         //   }

	            // タグIDを整数リストに変換
	         //   List<Integer> tagIdList = new ArrayList<>();
	        //    for (String tagId : tagIds) {
	         //       tagIdList.add(Integer.parseInt(tagId));
	         //   }

	            // 神社仏閣情報の取得
	        //    ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
	         //   List<ShrineAndTemple> shrineAndTempleList = shrineAndTempleDao.searchByTag(tagIdList);

	            // リクエストスコープにセット
	        //    req.setAttribute("shrineAndTempleList", shrineAndTempleList);

	            // 表示ページへフォワード
	      //      req.getRequestDispatcher("shrine_list.jsp").forward(req, res);



	//    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        // 選択されたタグIDを取得（例：複数選択）
	    //    String[] tagIds = req.getParameterValues("tagId");
	     //   List<Integer> tagIdList = new ArrayList<>();
	    //    for (String tagId : tagIds) {
	    //        tagIdList.add(Integer.parseInt(tagId));
	  //      }
	  //      ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
	        // タグから神社仏閣ID一覧を取得
	   //     ShrineAndTempleTagDao taggingDao = new ShrineAndTempleTagDao();
	 //       List<ShrineAndTemple> shrineAndTempleList = shrineAndTempleDao.searchByTag(tagIdList);


	        // リクエストスコープにセット
	   //     req.setAttribute("shrineAndTempleList", shrineAndTempleList);

	        // 表示ページへフォワード
	    //    req.getRequestDispatcher("shrine_list.jsp").forward(req, res);





