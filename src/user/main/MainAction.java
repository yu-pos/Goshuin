package user.main;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Event;
import bean.User;
import dao.EventDao;
import tool.Action;

public class MainAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

//    	//セッションにユーザーを登録（ログイン代わり。動作テスト用。ログイン部分が完成したら削除）
//    	UserDao userDao = new UserDao();
//    	HttpSession session = req.getSession(true);
//    	session.setAttribute("user", userDao.login("111-1111-1111", "test"));

    	HttpSession session = req.getSession(); // セッション
		User user = (User)session.getAttribute("user");

		// 現在のランク（6〜0）
        int currentRank = user.getRank();
        int goshuinCount = user.getGoshuinCount();

        // ランクアップに必要な御朱印数を計算
        int requiredForNext = 0;
        switch (currentRank) {
            case 6: requiredForNext = 3; break;   // 陸→伍
            case 5: requiredForNext = 7; break;   // 伍→肆
            case 4: requiredForNext = 10; break;  // 肆→参
            case 3: requiredForNext = 10; break;  // 参→弐
            case 2: requiredForNext = 10; break;  // 弐→壱
            case 1: requiredForNext = 20; break;  // 壱→零
            case 0: requiredForNext = 0; break;   // 零（最高ランク）
            default: requiredForNext = 0; break;
        }

        int remaining = 0;
        int couponCount = 0;
        int nextCouponRemaining = 0;

        if (currentRank > 0) {
            // ランクアップ可能な場合
            remaining = requiredForNext - goshuinCount;
            if (remaining < 0) remaining = 0;
        } else {
            // 最高ランク(零)の場合 → 30個ごとに商品券
            couponCount = goshuinCount / 30;
            nextCouponRemaining = 30 - (goshuinCount % 30);
            if (nextCouponRemaining == 30) {
                nextCouponRemaining = 0; // ちょうど区切りなら「残り0」
            }
        }

        // イベント情報（最新3件）
        EventDao eventDao = new EventDao();
        List<Event> allEvents = eventDao.getAll();
        List<Event> topEvents = allEvents.stream()
                                         .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                                         .limit(3)
                                         .collect(Collectors.toList());

        // JSPに渡す属性を設定
        req.setAttribute("currentRank", currentRank);
        req.setAttribute("remainingStamp", remaining);
        req.setAttribute("couponCount", couponCount);
        req.setAttribute("nextCouponRemaining", nextCouponRemaining);
        req.setAttribute("events", topEvents);

        // メイン画面へフォワード
        req.getRequestDispatcher("/user/main/main.jsp").forward(req, res);


    }
}