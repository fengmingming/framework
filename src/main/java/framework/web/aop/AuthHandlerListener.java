package framework.web.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	
	private Map<Class<? extends IsLoginHandler>, IsLoginHandler> loginHM = new ConcurrentHashMap<Class<? extends IsLoginHandler>, IsLoginHandler>();
	private Map<Class<? extends IChannelHandler>, IChannelHandler> channelHM = new ConcurrentHashMap<Class<? extends IChannelHandler>, IChannelHandler>();
	private Map<Class<? extends IRoleHandler>, IRoleHandler> roleHM = new ConcurrentHashMap<Class<? extends IRoleHandler>, IRoleHandler>();
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		//判断是否需要验证登陆，及验证
		IsLogin annotation = method.getMethodAnnotation(IsLogin.class);
		if(annotation != null){
			IsLoginHandler isLoginH = loginHM.get(annotation.handler());
			if(isLoginH == null){
				synchronized (annotation.handler()) {
					isLoginH = loginHM.get(annotation.handler());
					if(isLoginH == null){
						isLoginH = annotation.handler().newInstance();
						loginHM.put(annotation.handler(), isLoginH);
					}
				}
			}
			if(!isLoginH.handler(req, res, annotation.value())){
				return false;
			}
		}
		//判断是否需要验证渠道，及验证
		Channel channelA = method.getMethodAnnotation(Channel.class);
		if(channelA != null){
			IChannelHandler channelH = channelHM.get(channelA.handler());
			if(channelH == null){
				synchronized (channelA.handler()) {
					channelH = channelHM.get(channelA.handler());
					if(channelH == null){
						channelH = channelA.handler().newInstance();
						channelHM.put(channelA.handler(), channelH);
					}
				}
			}
			if(!channelH.handler(req, res, channelA.value())){
				return false;
			}
		}
		//判断是否需要验证角色，及验证
		Role roleA = method.getMethodAnnotation(Role.class);
		if(roleA != null){
			IRoleHandler roleH = roleHM.get(roleA.handler());
			if(roleH == null){
				synchronized (roleA.handler()) {
					roleH = roleHM.get(roleA.handler());
					if(roleH == null){
						roleH = roleA.handler().newInstance();
						roleHM.put(roleA.handler(), roleH);
					}
				}
			}
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
