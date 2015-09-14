package framework.web.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Channel {
	int ALL = 0;
	int WEB = 1;
	int CMS = 2;
	String value() default "";
	int channel() default ALL;
	Class<? extends IChannelHandler> handler() default DefaultChannelHandler.class;
}
