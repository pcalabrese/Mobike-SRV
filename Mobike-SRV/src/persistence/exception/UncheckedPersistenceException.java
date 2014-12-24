package persistence.exception;

public class UncheckedPersistenceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7721476397849744461L;
	
	public UncheckedPersistenceException()
	{
		
	}
	public UncheckedPersistenceException(String message)
	{
		super(message);
	}
	public UncheckedPersistenceException(Exception source)
	{
		super(source);
	}
	
	public UncheckedPersistenceException(String message,Exception source)
	{
		super(message,source);
	}
	

}
