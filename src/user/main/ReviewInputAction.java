package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import dao.ShrineAndTempleDao;
import tool.Action;

public class ReviewInputAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	        int shrineId = Integer.parseInt(req.getParameter("id"));
	        ShrineAndTempleDao shrineDao = new ShrineAndTempleDao();
	        ShrineAndTemple shrine = shrineDao.getById(shrineId);



	        req.setAttribute("shrineAndTemple", shrine);

	        req.getRequestDispatcher("review_input.jsp").forward(req, res);

	    }

}
