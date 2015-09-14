package framework.web.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import framework.web.session.HttpSessionAdapter;

public class DefaultHttpSessionListener implements HandlerInterceptor{
	
	private Map<String,Object> reqParams;
	
	private String domain = "*";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", this.domain);
		HttpSessionAdapter.setHttpSession(request.getSession(true));
		if(reqParams != null && reqParams.size() > 0){
			for(String key:reqParams.keySet()){
				request.setAttribute(key, reqParams.get(key));
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HttpSessionAdapter.destoryHttpSession();
	}

	public Map<String,Object> getReqParams() {
		return reqParams;
	}

	public void setReqParams(Map<String,Object> reqParams) {
		this.reqParams = reqParams;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}
