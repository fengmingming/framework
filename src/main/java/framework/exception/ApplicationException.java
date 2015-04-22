package framework.exception;


/**
 * 
 * 应用程序系统异常类
 * 
 * */
public class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = -9135645413708745906L;

	public ApplicationException(){
		super();
	}
	
	public ApplicationException(Throwable t){
		super(t);
	}
	
	public ApplicationException(String message){
		super(message);
	}
	
	public ApplicationException(String message,Throwable t){
		super(message,t);
	}

}
