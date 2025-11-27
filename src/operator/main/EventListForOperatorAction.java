package operator.main;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class EventListForOperatorAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("operator") == null) {
	        res.sendRedirect("login.jsp");
	        return;
	    }


	    EventDao dao = new EventDao();


	    // 通常のイベント一覧表示
	    List<Event> events = dao.getAll();
	    req.setAttribute("events", events);
	    req.setAttribute("hasEvent", !events.isEmpty());
	    req.getRequestDispatcher("/operator/main/event_list_for_operator.jsp").forward(req, res);
	}

}