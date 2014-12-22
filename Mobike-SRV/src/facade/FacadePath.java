package facade;

import model.Path;
import persistence.exception.PersistenceException;

public interface FacadePath{


	public boolean savePath(Path path) throws PersistenceException;
	public Path searchPath(Long id) throws PersistenceException;
}

