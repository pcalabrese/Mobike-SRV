package facade;

import model.Path;
import persistence.exception.PersistenceException;

public interface FacadePath{


	public boolean savePerson(Path path) throws PersistenceException;
	public Path searchPerson(Long id) throws PersistenceException;
}

