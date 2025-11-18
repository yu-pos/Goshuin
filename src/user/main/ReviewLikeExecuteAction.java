package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Review;
import dao.ReviewDao;
import tool.Action;

public class ReviewLikeExecuteAction extends Action  {

	 public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		 // パラメータからレビューIDを取得
	        int reviewId = Integer.parseInt(req.getParameter("reviewId"));
	        int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId")); // 再表示用

	        // DAOを使っていいねカウントを増やす
	        ReviewDao reviewDao = new ReviewDao();
	        Review review = reviewDao.getById(reviewId);
	        review.setLikeCount(review.getLikeCount() + 1);
	        boolean success = reviewDao.update(review);


	        // 成功したら元のページにリダイレクト
	        if (success) {
	            req.getRequestDispatcher("shrine_and_temple_info.jsp?shrineAndTempleId=" + shrineAndTempleId).forward(req, res);;
	        } else {
	            req.setAttribute("error", "いいねの更新に失敗しました。");
	            req.getRequestDispatcher("shrine_and_temple_info.jsp?shrineAndTempleId=" + shrineAndTempleId).forward(req, res);;




    }
}
}