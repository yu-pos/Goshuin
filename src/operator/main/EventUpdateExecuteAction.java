package operator.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class EventUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Map<String, String> errors = new HashMap<>();

        String idStr = req.getParameter("id");
        if (idStr == null) {
            res.sendRedirect("EventListForOperator.action");
            return;
        }

        int id = Integer.parseInt(idStr);

        String title = req.getParameter("title");
        String text = req.getParameter("text");

        // 元データ取得
        EventDao dao = new EventDao();
        Event event = dao.getById(id);

        if (event == null) {
            errors.put("notfound", "イベントが存在しません");
        }

        // 画像取得（未選択なら既存値保持）
        Part image = req.getPart("image");
        if (image != null && image.getSize() > 0) {
            String filename = image.getSubmittedFileName();
            image.write("C:/upload/" + filename);
            event.setImagePath("upload/" + filename);
        }

        event.setTitle(title);
        event.setText(text);

        if (errors.isEmpty()) {
            if (!dao.update(event)) {
                errors.put("updateFail", "更新に失敗しました");
            }
        }

        if (errors.isEmpty()) {
            res.sendRedirect("EventUpdateComplete.action");
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("event", event);
            req.getRequestDispatcher("/operator/main/event_update.jsp").forward(req, res);
        }
    }
}
