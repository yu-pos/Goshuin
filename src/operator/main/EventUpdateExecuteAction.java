package operator.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Event;
import dao.EventDao;
import tool.Action;
import tool.ImageUtils;

public class EventUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	String imagePath = null;
        Map<String, String> errors = new HashMap<>();

        // ID取得
        String idStr = req.getParameter("id");
        if (idStr == null) {
            res.sendRedirect("EventListForOperator.action");
            return;
        }
        int id = Integer.parseInt(idStr);

        String title = req.getParameter("title");
        String text = req.getParameter("text");

        // --- 元データ取得 ---
        EventDao dao = new EventDao();
        Event event = dao.getById(id);

        if (event == null) {
            errors.put("notfound", "イベントが存在しません");
        }

        // --- 画像アップロード処理 ---
        Part image = req.getPart("image");
	    boolean isImageUploaded = image != null && image.getSize() > 0;

        if (isImageUploaded && errors.isEmpty()) {
		    imagePath = ImageUtils.saveImage(image, "event", req);

		    if (imagePath == null) {
		    	errors.put("2", "画像のアップロードに失敗しました。");
		    }
		}

        // タイトル・本文更新
        event.setTitle(title);
        event.setText(text);

        // --- DB更新 ---
        if (errors.isEmpty()) {
        	event.setTitle(title);
            event.setText(text);

        	if(isImageUploaded) {
        		event.setImagePath(imagePath);
        	}

        	if (!dao.update(event)) {
                errors.put("updateFail", "更新に失敗しました");
            }
        }

        // --- 遷移 ---
        if (errors.isEmpty()) {
        	req.getRequestDispatcher("event_update_complete.jsp").forward(req, res);
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("event", event);
            req.getRequestDispatcher("/operator/main/event_update.jsp").forward(req, res);
        }
    }
}