package framework.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultRoleHandler implements IRoleHandler{

	@Override
	public boolean handler(HttpServletRequest req, HttpServletResponse res,
			String value) {
		return true;
	}

}
