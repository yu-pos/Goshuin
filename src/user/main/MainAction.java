package user.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import bean.Rank;
import bean.User;
import dao.EventDao;
import dao.OwnedVoucherDao;
import dao.RankDao;
import tool.Action;

public class MainAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // 現在のランク（数値）
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

        	OwnedVoucherDao ownedVoucherDao = new OwnedVoucherDao();


            couponCount = ownedVoucherDao.searchByUserId(user.getId()).size();
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

        // おみくじ
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDate today = nowDateTime.toLocalDate();

        LocalDateTime oldDateTime = user.getLastOmikujiAt();
        boolean isCanDrawOmikuji = false;

        if (oldDateTime == null) {
        	isCanDrawOmikuji = true;
        } else {
            LocalDate oldDate = oldDateTime.toLocalDate();
            if (!oldDate.isEqual(today)) {
            	isCanDrawOmikuji = true;
            }
        }


        // JSPに渡す属性を設定
        req.setAttribute("rankName", rankName);
        req.setAttribute("rankImagePath", rankImagePath);
        req.setAttribute("currentRank", currentRank);
        req.setAttribute("remainingStamp", remaining);
        req.setAttribute("couponCount", couponCount);
        req.setAttribute("nextCouponRemaining", nextCouponRemaining);
        req.setAttribute("goshuinCount", goshuinCount);
        req.setAttribute("eventsView", eventsView);
        req.setAttribute("isCanDrawOmikuji", isCanDrawOmikuji);

        // メイン画面へフォワード
        req.getRequestDispatcher("/user/main/main.jsp").forward(req, res);
    }
}