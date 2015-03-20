package Controller.exception;

public class WebServiceException extends Exception {

	private static final long serialVersionUID = 1169426381288170661L;

	public WebServiceException() {
		super();
	}

	public WebServiceException(String msg) {
		super(msg);
	}

	public WebServiceException(String msg, Exception e) {
		super(msg, e);
	}
}