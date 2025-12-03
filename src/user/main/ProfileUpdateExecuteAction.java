package user.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.User;
import dao.UserDao;
import tool.Action;
import tool.ImageUtils;

public class ProfileUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//セッションからユーザー情報を取得
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");


		User updatedUser = new User();
		boolean isMyGoshuinBookPublic;
		String imagePath = "";

		UserDao userDao = new UserDao();


		Map<String, String> errors = new HashMap<>();

		//リクエストパラメータ―の取得 2
		String userName = req.getParameter("userName");


		if(req.getParameter("isMyGoshuinBookPublic") != null) {
			isMyGoshuinBookPublic = true;
		} else {
			isMyGoshuinBookPublic = false;
		};

	    Part image = req.getPart("image");
	    boolean isImageUploaded = image != null && image.getSize() > 0;



		//DBからデータ取得 3
	    updatedUser = userDao.getById(user.getId());


		//ビジネスロジック 4
	    if (isImageUploaded && errors.isEmpty()) {
		    imagePath = ImageUtils.saveImage(image, "profile", req);

		    if (imagePath == null) {
		    	errors.put("1", "画像のアップロードに失敗しました。");
		    }
		}


		//DBへデータ保存 5
	    if (errors.isEmpty()) {
			updatedUser.setUserName(userName);
			updatedUser.setMyGoshuinBookPublic(isMyGoshuinBookPublic);

	    	if(isImageUploaded) {
	    		updatedUser.setProfileImagePath(imagePath);
	    	}

	    	if(!userDao.update(updatedUser)) {
	    		errors.put("2", "ユーザー情報の更新に失敗しました");
	    	}

	    	//セッションにも更新後の情報を保存
	    	session.setAttribute("user", updatedUser);
	    }


		//レスポンス値をセット 6
	    //なし

		//JSPへフォワード 7
		req.getRequestDispatcher("Profile.action").forward(req, res);

	}
}
