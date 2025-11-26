package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Rank;
import bean.User;
import dao.RankDao;
import tool.Action;

public class RankAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int currentRank = user.getRank();
        int goshuinCount = user.getGoshuinCount();

        // DBからランク情報を取得
        RankDao rankDao = new RankDao();
        Rank rank = rankDao.getById(currentRank);

        String rankName = (rank != null) ? rank.getName() : "";

        // DBにはファイル名だけが入っているので、ここでフルパスを組み立てる
        String rankImagePath;
        if (rank != null && rank.getImagePath() != null) {
            rankImagePath = req.getContextPath() + "/user/images/" + rank.getImagePath();
        } else {
            rankImagePath = req.getContextPath() + "/user/images/rank_default.png";
        }

        // ランクアップに必要な御朱印数
        int requiredForNext = (rank != null) ? rank.getRankUpQuantity() : 0;

        int remaining = 0;
        int couponCount = 0;
        int nextCouponRemaining = 0;

        if (currentRank > 0) {
            remaining = requiredForNext - goshuinCount;
            if (remaining < 0) remaining = 0;
        } else {
            couponCount = goshuinCount / 30;
            nextCouponRemaining = 30 - (goshuinCount % 30);
            if (nextCouponRemaining == 30) {
                nextCouponRemaining = 0;
            }
        }

        // JSPに渡す属性
        req.setAttribute("rankName", rankName);
        req.setAttribute("rankImagePath", rankImagePath);
        req.setAttribute("currentRank", currentRank);
        req.setAttribute("remainingStamp", remaining);
        req.setAttribute("couponCount", couponCount);
        req.setAttribute("nextCouponRemaining", nextCouponRemaining);
        req.setAttribute("goshuinCount", goshuinCount);

        // ランク画面へフォワード
        req.getRequestDispatcher("/user/main/rank.jsp").forward(req, res);
    }
}