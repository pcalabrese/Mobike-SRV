package model;

import java.util.List;

import persistence.exception.PersistenceException;

public interface PathRepository {

	
	public Path pathFromId(long id) throws PersistenceException;
	
	public List<Path> allPath() throws PersistenceException;
	
	public Path pathFromName(String name) throws PersistenceException;

	public void addPath(Path p) throws PersistenceException;
	
	public void removePath(Path p) throws PersistenceException;
	
	public void removePathFromId(long id) throws PersistenceException;
}
