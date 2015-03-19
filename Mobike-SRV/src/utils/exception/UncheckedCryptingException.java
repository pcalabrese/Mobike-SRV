package utils.exception;

public class UncheckedCryptingException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7721476397849744461L;
	
	public UncheckedCryptingException()
	{
		super("Encryption or Decryption Error");
	}
	public UncheckedCryptingException(String message)
	{
		super(message);
	}
	public UncheckedCryptingException(Exception source)
	{
		super(source);
	}
	
	public UncheckedCryptingException(String message,Exception source)
	{
		super(message,source);
	}
	

}
