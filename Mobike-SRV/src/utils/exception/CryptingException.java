package utils.exception;

/** 
 * Crypter Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see Exception
 * @version 2.0
 */
public class CryptingException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * default empty FilesystemException Constructor
	 */
	public CryptingException(){
		super();
	}
	
	/**
	 * Constructor with message
	 * @param message
	 */
	public CryptingException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with source
	 * @param source
	 */
	public CryptingException(Exception source){
		super(source);
	}

	/**
	 * Constructor with message and source
	 * @param message
	 * @param source
	 */
	public CryptingException(String message, Exception source){
		super(message,source);
	}
}