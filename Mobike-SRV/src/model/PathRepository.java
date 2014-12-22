package model;

import java.util.List;

public interface PathRepository {

	
	public Path pathFromId(long id);
	
	public List<Path> allPath();
	
	public Path pathFromName(String name);

	public boolean addPath(Path p);
	
	public void removePath(Path p);
	
	public void removePathFromId(long id);
}
