package persistence.exception;

public class UncheckedFilesystemException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7721476397849744461L;
	
	public UncheckedFilesystemException()
	{
		
	}
	public UncheckedFilesystemException(String message)
	{
		super(message);
	}
	public UncheckedFilesystemException(Exception source)
	{
		super(source);
	}
	
	public UncheckedFilesystemException(String message,Exception source)
	{
		super(message,source);
	}
	

}
