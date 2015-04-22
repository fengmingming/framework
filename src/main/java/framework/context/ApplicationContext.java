package framework.context;

import org.springframework.context.ApplicationContextAware;

public class ApplicationContext implements ApplicationContextAware{
	
	private static org.springframework.context.ApplicationContext applicationContext; 

	public static org.springframework.context.ApplicationContext getApplicationContext() {
		return ApplicationContext.applicationContext;
	}

	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext){
		ApplicationContext.applicationContext = applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
}
