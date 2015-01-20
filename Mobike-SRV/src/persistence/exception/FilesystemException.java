package persistence.exception;

/** 
 * File System Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see Exception
 * @version 2.0
 */
public class FilesystemException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * default empty FilesystemException Constructor
	 */
	public FilesystemException(){
		super();
	}
	
	/**
	 * Constructor with message
	 * @param message
	 */
	public FilesystemException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with source
	 * @param source
	 */
	public FilesystemException(Exception source){
		super(source);
	}

	/**
	 * Constructor with message and source
	 * @param message
	 * @param source
	 */
	public FilesystemException(String message, Exception source){
		super(message,source);
	}
}