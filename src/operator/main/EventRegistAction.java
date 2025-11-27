package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class EventRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));

        EventDao dao = new EventDao();
        Event event = dao.getById(id);

        req.setAttribute("event", event);

        req.getRequestDispatcher("/operator/main/event_regist.jsp").forward(req, res);
    }
}
