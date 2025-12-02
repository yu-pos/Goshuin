package user.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import dao.ShrineAndTempleDao;
import tool.Action;

public class ReviewInputAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    try {
	        int shrineId = Integer.parseInt(request.getParameter("id"));
	        ShrineAndTempleDao shrineDao = new ShrineAndTempleDao();
	        ShrineAndTemple shrine = shrineDao.getById(shrineId);

	        if (shrine == null) {
	            request.setAttribute("error", "指定された神社仏閣は存在しません。");
	            return "error.jsp";
	        }

	        request.setAttribute("shrineAndTemple", shrine);
	        return "review_input.jsp";

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "口コミ投稿画面の表示に失敗しました。");
	        return "error.jsp";
	    }
	}

}
