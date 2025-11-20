package user.main;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Review;
import dao.ReviewDao;
import tool.Action;

public class ReviewPostExecuteAction extends Action {

    // 投稿できないNGワード一覧（例）
    private static final List<String> NG_WORDS = Arrays.asList("死ね", "バカ", "差別", "禁止語");

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // パラメータ取得
            int userId = Integer.parseInt(request.getParameter("userId"));
            int shrineAndTempleId = Integer.parseInt(request.getParameter("shrineAndTempleId"));
            String text = request.getParameter("text");
            String imagePath = request.getParameter("imagePath"); // ファイルアップロード処理が別途必要

            // バリデーション①：未入力チェック
            if (text == null || text.trim().isEmpty()) {
                request.setAttribute("error", "口コミ内容を入力してください。");
                request.getRequestDispatcher("review_input.jsp").forward(request, response);
                return;
            }

            // バリデーション②：NGワードチェック
            for (String ng : NG_WORDS) {
                if (text.contains(ng)) {
                    request.setAttribute("error", "投稿できない単語が含まれています。");
                    request.getRequestDispatcher("review_input.jsp").forward(request, response);
                    return;
                }
            }

            // Reviewインスタンス生成
            Review review = new Review();
            review.setUserId(userId);
            review.setShrineAndTempleId(shrineAndTempleId);
            review.setText(text);
            review.setImagePath(imagePath);
            review.setLikeCount(0);

            // DAOで登録処理
            ReviewDao reviewDao = new ReviewDao();
            boolean success = reviewDao.insert(review);

            if (success) {
                request.getRequestDispatcher("review_post_complete.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "口コミの登録に失敗しました。");
                request.getRequestDispatcher("review_input.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                request.setAttribute("error", "システムエラーが発生しました。");
                request.getRequestDispatcher("review_input.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


