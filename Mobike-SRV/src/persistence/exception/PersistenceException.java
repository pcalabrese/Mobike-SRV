package persistence.exception;

public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenceException(){
		super();
	}
	
	public PersistenceException(String message) {
		super(message);
	}
	
	public PersistenceException(Exception source){
		super(source);
	}

	public PersistenceException(String message, Exception source){
		super(message,source);
	}
}