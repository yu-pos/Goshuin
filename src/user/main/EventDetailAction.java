package user.main;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class EventDetailAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("user") == null) {
	        res.sendRedirect("login.jsp");
	        return;
	    }

	    String eventId = req.getParameter("eventId");

	    EventDao dao = new EventDao();


	    Event event = dao.getById(Integer.parseInt(eventId));
	    req.setAttribute("event", event);
	    req.getRequestDispatcher("event_detail.jsp").forward(req, res);


	}

}