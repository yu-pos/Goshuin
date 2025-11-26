package user.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

//    	String error = "";
//    	try {
	        String url = "";
	        String telNumber = "";
	        String password = "";
	        UserDao userDao = new UserDao();
	        User user = null;

	        // 入力値取得
	        telNumber = req.getParameter("tel");
	        password  = req.getParameter("password");

	        System.out.println("[DEBUG](LoginExecute) telNumber = " + telNumber);
	        System.out.println("[DEBUG](LoginExecute) password = " + password);
	        // 🔹 電話番号の「数字以外」を全部削除（ハイフン・空白・全角など対応）
	        if (telNumber != null) {
	            telNumber = telNumber.replaceAll("[^0-9]", "");
	        }

	        // エラーリスト（ログイン画面は List<String> でOK）
	        List<String> errors = new ArrayList<>();

	        // 🔹 電話番号形式チェック（10〜11桁の数字）
	        if (telNumber == null || !telNumber.matches("\\d{10,11}")) {
	            errors.add("有効な電話番号を入力してください");
	        }

	        // 電話番号形式に問題があれば、その時点でログイン画面へ戻す
	        if (!errors.isEmpty()) {
	            System.out.println("[DEBUG](LoginExecute) ログイン失敗-不正な電話番号");
	            req.setAttribute("errors", errors);
	            // 入力し直し用に、元の値を戻す（ハイフン付きで表示したいなら元の req.getParameter を別に持つ）
	            req.setAttribute("tel", telNumber);
	            url = "login.jsp";
	            req.getRequestDispatcher(url).forward(req, res);
	            return;
	        }

	        // 🔹 ここまで来たら電話番号形式はOK → 認証処理へ
	        user = userDao.login(telNumber, password);

	        if (user != null) { // 認証成功の場合
	        	System.out.println("[DEBUG](LoginExecute)ログイン成功");
	            HttpSession session = req.getSession(true);
	            session.setAttribute("user", user);

	            // ★ ログインポイント処理（前に作ったやつ）
	            LocalDateTime nowDateTime = LocalDateTime.now();
	            LocalDate today = nowDateTime.toLocalDate();

	            LocalDateTime oldDateTime = user.getLastLoginAt();
	            boolean shouldGivePoint = false;

	            if (oldDateTime == null) {
	                // 初回ログイン
	                shouldGivePoint = true;
	            } else {
	                LocalDate oldDate = oldDateTime.toLocalDate();
	                if (!oldDate.isEqual(today)) {
	                    // 昨日以前 → 今日の初回ログイン
	                    shouldGivePoint = true;
	                }
	            }

	            if (shouldGivePoint) {
	                int addPoint = 1; // 付与ポイント

	                user.setPoint(user.getPoint() + addPoint);
	                req.setAttribute("loginPointMessage", "ログインポイントが付与されました");
	            }

	            // ログインした時刻を最終ログインに更新
	            user.setLastLoginAt(nowDateTime);
	            userDao.update(user);
	            session.setAttribute("user", user);

	            url = "Main.action";

	        } else {
	            // 認証失敗の場合（電話番号形式はOKだが、ユーザーがいない or パスワード不一致）
	            errors.add("電話番号またはパスワードが確認できませんでした");
	            req.setAttribute("errors", errors);

	            // 入力された電話番号を再表示用にセット
	            req.setAttribute("tel", telNumber);

	            url = "login.jsp";

	        }
	        req.getRequestDispatcher(url).forward(req, res);
//    	} catch (Exception e) {
//    		StringWriter sw = new StringWriter();
//    		PrintWriter pw = new PrintWriter(sw);
//    		e.printStackTrace(pw);
//
//    		error = sw.toString();
//    		req.setAttribute("error", error);
//    		req.getRequestDispatcher("error.jsp").forward(req, res);
//    	}


    }
}
