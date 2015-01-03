package persistence.fs;



import persistence.exception.FilesystemException;

public interface RouteWriter {
	
	public String write(String content,String filename) throws FilesystemException;

}
