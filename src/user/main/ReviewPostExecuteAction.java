package user.main;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Review;
import bean.User;
import dao.ReviewDao;
import tool.Action;
import tool.ImageUtils;

public class ReviewPostExecuteAction extends Action {

    // 投稿できないNGワード一覧（例）
    private static final List<String> NG_WORDS = Arrays.asList("殺す", "バカ", "差別", "死ね","炎上確定笑笑"
    		+ "うんこ","潰れてしまえ");

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // パラメータ取得

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                request.setAttribute("error", "ログインしてください。");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }


            int shrineAndTempleId = Integer.parseInt(request.getParameter("shrineAndTempleId"));
            String text = request.getParameter("text");
            String imagePath = null; // ファイルアップロード処理が別途必要

            // バリデーション①：未入力チェック
            if (text == null || text.trim().isEmpty()) {
                request.setAttribute("error", "口コミ内容を入力してください。");
                request.setAttribute("text", text);
                request.setAttribute("shrineAndTempleId", shrineAndTempleId);
                request.getRequestDispatcher("review_input.jsp").forward(request, response);
                return;
            }

            // バリデーション②：NGワードチェック
            for (String ng : NG_WORDS) {
                if (text.contains(ng)) {
                	request.setAttribute("error", "投稿できない単語が含まれています。");
                    request.setAttribute("shrineAndTempleId", shrineAndTempleId);
                    request.setAttribute("text", text); // 入力内容を保持
                    request.getRequestDispatcher("review_input.jsp").forward(request, response);
                    return;

                }
            }

            Part photo = request.getPart("photo");
            if (photo != null && photo.getSize() > 0) {
            	imagePath = ImageUtils.saveImage(photo, "review", request);
            }

            // Reviewインスタンス生成
            Review review = new Review();
            review.setUserId(user.getId());
            review.setShrineAndTempleId(shrineAndTempleId);
            review.setText(text);
            review.setImagePath(imagePath);
            review.setLikeCount(0);

            // DAOで登録処理
            ReviewDao reviewDao = new ReviewDao();
            boolean success = reviewDao.insert(review);

            if (success) {
            	request.setAttribute("shrineAndTempleId", shrineAndTempleId);
            	System.out.println("shrineAndTempleId = " + shrineAndTempleId);
                request.getRequestDispatcher("review_post_complete.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "口コミの登録に失敗しました。");
                request.getRequestDispatcher("review_input.jsp").forward(request, response);

            }


        } catch (Exception e) {
            e.printStackTrace();
            try {
                request.setAttribute("error", "システムエラーが発生しました。");

                // ★ 追加：入力内容を保持
                request.setAttribute("text", request.getParameter("text"));
                request.setAttribute("shrineAndTempleId", request.getParameter("shrineAndTempleId"));

                request.getRequestDispatcher("review_input.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}




