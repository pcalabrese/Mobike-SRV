package persistence.fs;



import persistence.exception.FilesystemException;

public interface RouteIO {
	
	public String write(String content,String filename) throws FilesystemException;
	
	public String read(String filepath) throws FilesystemException;

}
