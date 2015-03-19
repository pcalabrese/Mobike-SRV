package utils.exception;

public class UncheckedAuthenticationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7721476397849744461L;
	
	public UncheckedAuthenticationException()
	{
		super("Authentication Error");
	}
	public UncheckedAuthenticationException(String message)
	{
		super(message);
	}
	public UncheckedAuthenticationException(Exception source)
	{
		super(source);
	}
	
	public UncheckedAuthenticationException(String message,Exception source)
	{
		super(message,source);
	}
	

}
