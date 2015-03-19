package utils.exception;

/** 
 * Authenticator Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see Exception
 * @version 2.0
 */
public class AuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * default empty FilesystemException Constructor
	 */
	public AuthenticationException(){
		super();
	}
	
	/**
	 * Constructor with message
	 * @param message
	 */
	public AuthenticationException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with source
	 * @param source
	 */
	public AuthenticationException(Exception source){
		super(source);
	}

	/**
	 * Constructor with message and source
	 * @param message
	 * @param source
	 */
	public AuthenticationException(String message, Exception source){
		super(message,source);
	}
}