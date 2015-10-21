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
	public IsLoginHandler getIsLoginH() {
		return isLoginH;
	}

	public void setIsLoginH(IsLoginHandler isLoginH) {
		this.isLoginH = isLoginH;
	}

	public IChannelHandler getChannelH() {
		return channelH;
	}

	public void setChannelH(IChannelHandler channelH) {
		this.channelH = channelH;
	}

	public IRoleHandler getRoleH() {
		return roleH;
	}

	public void setRoleH(IRoleHandler roleH) {
		this.roleH = roleH;
	}

	private IChannelHandler channelH;
	private IRoleHandler roleH;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		IsLogin isLoginA = method.getMethodAnnotation(IsLogin.class);
		if(isLoginH != null){
			if(isLoginH.handler(req, res, isLoginA == null?true:isLoginA.value())){
				if(isLoginA == null||isLoginA.value()){
					Role roleA = method.getMethodAnnotation(Role.class);
					if(roleH != null && !roleH.handler(req, res, roleA == null?false:roleA.value())){
						return false;
					}
					Channel channelA = method.getMethodAnnotation(Channel.class);
					if(channelH != null && !channelH.handler(req, res, channelA == null?new String[]{}:channelA.value())){
						return false;
					}
				}
			}else{
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
