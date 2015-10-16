package framework.web.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import framework.web.auth.Channel;
import framework.web.auth.IChannelHandler;
import framework.web.auth.IRoleHandler;
import framework.web.auth.IsLogin;
import framework.web.auth.IsLoginHandler;
import framework.web.auth.Role;

/**
 * 
 * 验证handler 验证用户是否登陆，用户渠道，用户角色
 * 
 * */
public class AuthHandlerListener implements HandlerInterceptor{
	
	private IsLoginHandler isLoginH;
	private IChannelHandler channelH;
	private IRoleHandler roleH;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		IsLogin annotation = method.getMethodAnnotation(IsLogin.class);
		if(annotation != null && isLoginH != null){
			if(!isLoginH.handler(req, res, annotation.value())){
				return false;
			}
		}
		Channel channelA = method.getMethodAnnotation(Channel.class);
		if(channelA != null&&channelH != null){
			if(!channelH.handler(req, res, channelA.value())){
				return false;
			}
		}
		Role roleA = method.getMethodAnnotation(Role.class);
		if(roleA != null&&roleH != null){
			if(!roleH.handler(req, res, channelA.value())){
				return false;
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
		
	}

}
