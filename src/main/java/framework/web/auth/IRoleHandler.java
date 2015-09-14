package framework.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRoleHandler {
	public boolean handler(HttpServletRequest req,HttpServletResponse res,String value);
}
