package user.main;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // 現在のランク（数値）
        int currentRank = user.getRank();
        int goshuinCount = user.getGoshuinCount();

        // 数値ランクを漢字に変換
        String rankName;
        switch (currentRank) {
            case 6: rankName = "陸"; break;
            case 5: rankName = "伍"; break;
            case 4: rankName = "肆"; break;
            case 3: rankName = "参"; break;
            case 2: rankName = "弐"; break;
            case 1: rankName = "壱"; break;
            case 0: rankName = "零"; break;
            default: rankName = ""; break;
        }

        // ランクアップに必要な御朱印数を計算
        int requiredForNext = 0;
        switch (currentRank) {
            case 6: requiredForNext = 3; break;
            case 5: requiredForNext = 7; break;
            case 4:
            case 3:
            case 2: requiredForNext = 10; break;
            case 1: requiredForNext = 20; break;
            case 0: requiredForNext = 0; break;
            default: requiredForNext = 0; break;
        }

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

        // イベント情報（最新3件）
        EventDao eventDao = new EventDao();
        List<Event> allEvents = eventDao.getAll();
        List<Event> topEvents = allEvents.stream()
                                         .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                                         .limit(3)
                                         .collect(Collectors.toList());

        // LocalDateTime → Date に変換したビュー用リスト
        List<Map<String, Object>> eventsView = new ArrayList<>();
        for (Event e : topEvents) {
            Map<String, Object> vm = new HashMap<>();
            vm.put("id", e.getId());
            vm.put("title", e.getTitle());
            vm.put("text", e.getText());
            vm.put("imagePath", e.getImagePath());

            Date createdAtDate = Date.from(e.getCreatedAt()
                .atZone(ZoneId.systemDefault())
                .toInstant());
            vm.put("createdAtDate", createdAtDate);

            eventsView.add(vm);
        }

        // JSPに渡す属性を設定
        req.setAttribute("rankName", rankName);
        req.setAttribute("currentRank", currentRank);
        req.setAttribute("remainingStamp", remaining);
        req.setAttribute("couponCount", couponCount);
        req.setAttribute("nextCouponRemaining", nextCouponRemaining);
        req.setAttribute("eventsView", eventsView);

        // メイン画面へフォワード
        req.getRequestDispatcher("/user/main/main.jsp").forward(req, res);
    }
}