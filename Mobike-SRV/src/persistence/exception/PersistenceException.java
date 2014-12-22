package persistence.exception;

public class PersistenceException extends Exception {

//	private String message;

	private static final long serialVersionUID = 1L;

	public PersistenceException(){
		super();
	}
	
	public PersistenceException(String message) {
		super(message);
//		this.message = message;
	}

}