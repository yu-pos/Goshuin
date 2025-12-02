package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {

	public abstract String execute(
			HttpServletRequest req, HttpServletResponse res
		) throws Exception;

}
