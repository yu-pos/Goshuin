package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class ReviewInputAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	        int shrineId = Integer.parseInt(req.getParameter("id"));



	        req.setAttribute("shrineAndTempleId", shrineId);

	        req.getRequestDispatcher("review_input.jsp").forward(req, res);

	    }

}
