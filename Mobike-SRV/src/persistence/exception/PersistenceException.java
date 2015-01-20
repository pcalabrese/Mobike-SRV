package persistence.exception;
/** 
 * Persistence Exception Class
 * @author Paolo, Bruno, Marco, Andrea
 * @see Exception
 * @version 2.0
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * default empty PersistenceException Constructor
	 */
	public PersistenceException(){
		super();
	}
	
	/**
	 * Constructor with message
	 * @param message
	 */
	public PersistenceException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with source
	 * @param source
	 */
	public PersistenceException(Exception source){
		super(source);
	}
	
	/**
	 * Constructor with message and source
	 * @param message
	 * @param source
	 */
	public PersistenceException(String message, Exception source){
		super(message,source);
	}
}