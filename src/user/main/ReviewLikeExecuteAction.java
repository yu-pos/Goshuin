package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.ReviewDao;
import tool.Action;

public class ReviewLikeExecuteAction extends Action  {

	 @Override
	 public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 req.setCharacterEncoding("UTF-8");
		 res.setContentType("application/json; charset=UTF-8");
		 
		 HttpSession session = req.getSession();
		 // ログインユーザー取得
		 User user = (User) session.getAttribute("user");
		 int userId = user.getId();
		 
		 // JSから送られてくる値
		 int reviewId = Integer.parseInt(req.getParameter("reviewId"));
		 String mode = req.getParameter("mode");   // add / remove
		 
		 ReviewDao reviewDao = new ReviewDao();
		 boolean result = false;
		 
		 // 追加 or 削除
		 if ("add".equals(mode)) {
			 result = reviewDao.insertLike(reviewId, userId);
		 } else if ("remove".equals(mode)) {
			 result = reviewDao.deleteLike(reviewId, userId);
		 }
		 
		 // 現在のいいね数を取得
		 int likeCount = reviewDao.getById(reviewId).getLikeCount();
		 
		 // JSONで返却
		 String json = String.format("{\"success\":%b, \"likeCount\":%d}", result, likeCount);
		 res.getWriter().write(json);
	}
}




