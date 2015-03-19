package Controller.exception;

/** 
 * UncheckedControllerException Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see RuntimeException
 * @version 2.0
 */
public class UncheckedControllerException extends RuntimeException{

	private static final long serialVersionUID = 7721476397849744461L;
	
	/**
	 * default empty UncheckedControllerException Constructor
	 */
	public UncheckedControllerException()
	{
		
	}
	
	
	public UncheckedControllerException(String message)
	{
		super(message);
	}
	
	public UncheckedControllerException(Exception source)
	{
		super(source);
	}
	
	public UncheckedControllerException(String message,Exception source)
	{
		super(message,source);
	}
	

}
