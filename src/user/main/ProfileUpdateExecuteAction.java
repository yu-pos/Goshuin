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

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            res.sendRedirect("Login.action");
            return;
        }

        UserDao userDao = new UserDao();
        Map<String, String> errors = new HashMap<>();

        // 入力取得（ユーザー名＋画像＋My御朱印帳公開フラグ）
        String userName = req.getParameter("userName");

        // ❌これだと hidden の false を拾うことがある
        // boolean isMyGoshuinBookPublic = Boolean.parseBoolean(req.getParameter("isMyGoshuinBookPublic"));

	     String[] pubValues = req.getParameterValues("isMyGoshuinBookPublic");
	     boolean isMyGoshuinBookPublic = false;
	     if (pubValues != null) {
	         for (String v : pubValues) {
	             if ("true".equalsIgnoreCase(v) || "on".equalsIgnoreCase(v)) {
	                 isMyGoshuinBookPublic = true;
	                 break;
	             }
	         }
	     }

        Part image = req.getPart("image");
        boolean isImageUploaded = image != null && image.getSize() > 0;

        // DBから最新ユーザー取得
        User updatedUser = userDao.getById(user.getId());
        if (updatedUser == null) {
            res.sendRedirect("Login.action");
            return;
        }

        // 画像保存
        String imagePath = null;
        if (isImageUploaded) {
            imagePath = ImageUtils.saveImage(image, "profile", req);
            if (imagePath == null) {
                errors.put("1", "画像のアップロードに失敗しました。");
            }
        }

        // DB更新
        if (errors.isEmpty()) {
            updatedUser.setUserName(userName);

            if (isImageUploaded) {
                updatedUser.setProfileImagePath(imagePath);
            }

            // ✅ 追加：公開フラグを反映（DAOがこの列をUPDATEするので、ここで必ずセット）
            updatedUser.setMyGoshuinBookPublic(isMyGoshuinBookPublic);

            if (!userDao.update(updatedUser)) {
                errors.put("2", "ユーザー情報の更新に失敗しました");
            } else {
                // セッション更新（表示ズレ防止）
                session.setAttribute("user", userDao.getById(updatedUser.getId()));

                // ✅ 成功 → 完了画面へ
                req.getRequestDispatcher("profile_update_complete.jsp").forward(req, res);
                return;
            }
        }

        // 失敗 → 編集画面へ戻す（ページ名は実ファイルに合わせて）
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("profile_edit.jsp").forward(req, res);
    }
}
