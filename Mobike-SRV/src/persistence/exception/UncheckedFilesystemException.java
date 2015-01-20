package persistence.exception;

/** 
 * UncheckedFileSystemException Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see RuntimeException
 * @version 2.0
 */
public class UncheckedFilesystemException extends RuntimeException{

	private static final long serialVersionUID = 7721476397849744461L;
	
	/**
	 * default empty UncheckedFilesystemException Constructor
	 */
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
