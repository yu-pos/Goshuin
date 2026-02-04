package user.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class OmikujiAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

		HashMap<String, String> omikujiData = new HashMap<>();

		omikujiData.put("✨ 大吉 ✨", "最高の運勢です！新しいことを始めるのに最適な日。<br>笑顔を忘れず進めば、すべてがうまくいくでしょう🌸");
		omikujiData.put("🌟 中吉 🌟", "良い流れが来ています。努力が実りやすい時期。<br>自信を持って行動しましょう🍀");
		omikujiData.put("🙂 小吉 🙂", "穏やかに過ごせる一日。焦らずにコツコツ進むと吉。");
		omikujiData.put("😌 吉 😌", "可もなく不可もなくですが、心穏やかに過ごせば運気UP！");
		omikujiData.put("⚠️ 末吉 ⚠️", "あと少しでチャンス到来。無理せず備えるのが大切です。");
		omikujiData.put("💦 凶 💦", "慎重に行動しましょう。落ち着いて判断すれば大丈夫です。");


		//おみくじをランダムに引く
		List<Map.Entry> list = new ArrayList<Map.Entry>(omikujiData.entrySet());
		int index = new Random().nextInt(list.size());
		Map.Entry result = list.get(index);


		UserDao userDao = new UserDao();
		//現在時刻をlastOmikujiAtに設定
		user.setLastOmikujiAt(LocalDateTime.now());
		userDao.update(user);
		session.setAttribute("user", user);


		req.setAttribute("result", result);
		req.getRequestDispatcher("omikuji.jsp").forward(req, res);


	}

}
