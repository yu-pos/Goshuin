package user.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FavoriteShrineAndTemple;
import bean.User;
import dao.FavoriteShrineAndTempleDao;
import tool.Action;

public class FavoriteRegistExecuteAction extends Action{



		   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		        int userId = ((User) req.getSession().getAttribute("user")).getId();

		        FavoriteShrineAndTempleDao dao = new FavoriteShrineAndTempleDao();
		        List<FavoriteShrineAndTemple> favorites = dao.searchByUser(userId);











		        req.setAttribute("favorites", favorites);
		        req.getRequestDispatcher("favorite_list.jsp").forward(req, res);
//登録数が三個未満の場合登録ができる









	    // リクエストスコープにセット

	}
}
