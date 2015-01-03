package persistence.exception;

public class FilesystemException extends Exception {

	private static final long serialVersionUID = 1L;

	public FilesystemException(){
		super();
	}
	
	public FilesystemException(String message) {
		super(message);
	}
	
	public FilesystemException(Exception source){
		super(source);
	}

	public FilesystemException(String message, Exception source){
		super(message,source);
	}
}