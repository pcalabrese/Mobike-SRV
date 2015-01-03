package persistence.fs;



import persistence.exception.FilesystemException;

public interface PathWriter {
	
	public String write(String content,String filename) throws FilesystemException;

}
