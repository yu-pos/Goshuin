package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class LoginAction extends Action {
	 @Override
	    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        // 運営者ログイン画面へフォワード
	        req.getRequestDispatcher("login.jsp").forward(req, res);
	 	}
}
