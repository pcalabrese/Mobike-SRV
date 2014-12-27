package facade;

import java.util.List;
import persistence.PathRepository;
import persistence.exception.*;
import persistence.mysql.PathMySQL;
import model.Path;

	public class FacadeMainImpl implements FacadeMain {
		protected PathRepository pathRep;
				
		public FacadeMainImpl(){
			
			pathRep = new PathMySQL();
			
		}
		
		@Override
		public void addPath(String name, String description, String url,int length, long duration, long userId) {
			
			try{
			 Path path = new Path();
			 path.setName(name);
			 path.setDescription(description);
			 path.setLength(length);
			 path.setDuration(duration);
			 path.setUrl(url);
			 
			 
			 pathRep.addPath(path);
			}
			catch (Exception e){
				throw new UncheckedPersistenceException("Error during path creation",e);
			}
						
		}

		@Override
		public Path searchPath(Long id) {
			Path path = null;
			PathRepository pathREP = new PathMySQL();
			try{
			path = pathREP.pathFromId(id);
			}
			catch(PersistenceException pex){}
			return path;
		}

		@Override
		public List<Path> retriveAllPaths() {
			// TODO Auto-generated method stub
			return null;
		}
	}
