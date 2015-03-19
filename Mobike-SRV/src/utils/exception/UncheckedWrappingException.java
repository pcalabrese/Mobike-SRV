package utils.exception;

public class UncheckedWrappingException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7721476397849744461L;
	
	public UncheckedWrappingException()
	{
		super("Error Wrapping Json");
	}
	public UncheckedWrappingException(String message)
	{
		super(message);
	}
	
	public UncheckedWrappingException(Exception source)
	{
		super(source);
	}
	
	public UncheckedWrappingException(String message,Exception source)
	{
		super(message,source);
	}
	

}
