package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Review;
import dao.ReviewDao;
import tool.Action;

public class ReviewLikeExecuteAction extends Action  {

	 @Override
	    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        // パラメータからレビューIDと神社仏閣IDを取得
	        int reviewId = Integer.parseInt(req.getParameter("reviewId"));
	        int shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));

	        // DAOを使っていいねカウントを増やす
	        ReviewDao reviewDao = new ReviewDao();
	        Review review = reviewDao.getById(reviewId);
	        review.setLikeCount(review.getLikeCount() + 1);
	        boolean success = reviewDao.update(review);

	        // メッセージをセット
	        if (success) {
	            req.setAttribute("likeMessage", "いいねしました！");
	        } else {
	            req.setAttribute("errorMessage", "いいねの更新に失敗しました。");
	        }

	        // shrineAndTempleIdを保持して再表示用アクションへフォワード
	        req.setAttribute("shrineAndTempleId", shrineAndTempleId);
	        req.getRequestDispatcher("ShrineAndTempleInfo.action").forward(req, res);
	    }
	}




