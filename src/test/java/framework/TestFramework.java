package framework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestFramework {
	
	public static final ApplicationContext context;

	static{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}
	
}
