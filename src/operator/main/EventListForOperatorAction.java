package operator.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Event;
import dao.EventDao;
import tool.Action;
import tool.ImageUtils;

public class EventListForOperatorAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("operator") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        EventDao dao = new EventDao();
        List<Event> events = dao.getAll();

        // JSPに渡す属性を設定
        req.setAttribute("events", events);
        req.setAttribute("hasEvent", !events.isEmpty());

        // 画像の基準パスをセッションに保存
        session.setAttribute("basePath", ImageUtils.getBasePath());

        // フォワード
        req.getRequestDispatcher("/operator/main/event_list_for_operator.jsp").forward(req, res);
    }
}