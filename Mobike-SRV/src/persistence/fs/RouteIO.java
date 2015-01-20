package persistence.fs;



import persistence.exception.FilesystemException;

/**
 * @author br1
 *
 */
public interface RouteIO {
	
	/**
	 * @param content
	 * @param filename
	 * @return
	 * @throws FilesystemException
	 */
	public String write(String content,String filename) throws FilesystemException;
	
	/**
	 * @param filepath
	 * @return
	 * @throws FilesystemException
	 */
	public String read(String filepath) throws FilesystemException;

}
