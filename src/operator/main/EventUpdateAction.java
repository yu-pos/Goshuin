package operator.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class EventUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    // フォームから送られてくるID
	    String idStr = req.getParameter("eventId");

	    if (idStr == null || idStr.isEmpty()) {
	        res.sendRedirect("EventListForOperator.action");
	        return;
	    }

	    int id = Integer.parseInt(idStr);
	    EventDao dao = new EventDao();
	    Event event = dao.getById(id);

	    req.setAttribute("event", event);
	    req.getRequestDispatcher("/operator/main/event_update.jsp").forward(req, res);
	}
}
