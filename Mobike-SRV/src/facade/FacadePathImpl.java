package facade;

import model.PathRepository;
import persistence.DAO.PathDAO;
import persistence.exception.PersistenceException;
import model.Path;

	public class FacadePathImpl implements FacadePath {
		
		public boolean savePath(Path path) throws PersistenceException {
			PathRepository pathREP = new PathDAO();
			try{
			return pathREP.addPath(path);
			}
			catch (PersistenceException pex){return false;}
		}

		public Path searchPath(Long id) throws PersistenceException {
			Path path = null;
			PathRepository pathREP = new PathDAO();
			try{
			path = pathREP.pathFromId(id);
			}
			catch(PersistenceException pex){}
			return path;
		}
	}
