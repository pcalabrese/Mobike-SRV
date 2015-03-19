package utils.exception;

/** 
 * File System Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see Exception
 * @version 2.0
 */
public class WrappingException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * default empty FilesystemException Constructor
	 */
	public WrappingException(){
		super();
	}
	
	/**
	 * Constructor with message
	 * @param message
	 */
	public WrappingException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with source
	 * @param source
	 */
	public WrappingException(Exception source){
		super(source);
	}

	/**
	 * Constructor with message and source
	 * @param message
	 * @param source
	 */
	public WrappingException(String message, Exception source){
		super(message,source);
	}
}