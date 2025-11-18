package user.main;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Review;
import bean.ShrineAndTemple;
import dao.ReviewDao;
import dao.ShrineAndTempleDao;
import tool.Action;

public  class ShrineAndTempleInfoAction extends Action  {

	public  void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


	//ローカル変数の指定
	HttpSession session = req.getSession();

    int shrineAndTempleId= Integer.parseInt(req.getParameter("shrineAndTempleId"));
	ShrineAndTempleDao shrineAndTempleDao=new ShrineAndTempleDao();
    // リクエストパラメータから神社仏閣IDを取得


    // DAOのインスタンス化
    ReviewDao reviewDao = new ReviewDao();

    // 神社仏閣情報を取得
    ShrineAndTemple shrineAndTemple = shrineAndTempleDao.getById(shrineAndTempleId);

    // 口コミ一覧を取得
    List<Review> reviewList = reviewDao.searchByShrineAndTempleId(shrineAndTempleId);

    // リクエストスコープにセット
    req.setAttribute("shrineAndTemple", shrineAndTemple);
    req.setAttribute("reviewList", reviewList);

    // JSPへフォワード
    req.getRequestDispatcher("shrine_and_temple_info.jsp").forward(req, res);



	}
}