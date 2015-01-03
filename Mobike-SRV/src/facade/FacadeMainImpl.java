package facade;

import java.util.Date;
import java.util.List;

import persistence.PathRepository;
import persistence.exception.*;
import persistence.mysql.PathMySQL;
import persistence.fs.*;
import model.Path;


	public class FacadeMainImpl implements FacadeMain {
		protected PathRepository pathRep;
				
		public FacadeMainImpl(){
			
			pathRep = new PathMySQL();
			
		}
		
		@Override
		public void createPath(String name, String description,double length, long duration, Date uploadDate,String creatorEmail, String gpxString) {
			 
			
			 Path path = new Path();
			 path.setName(name);
			 path.setDescription(description);
			 path.setLength(length);
			 path.setDuration(duration);
			 path.setGpxString(gpxString);
			 path.setCreatorEmail(creatorEmail);
			 path.setUploadDate(uploadDate);
			 String url = null;
			
			
			try {
				PathWriter writer = new GpxWriter();
				url = writer.write(gpxString, path.getName());
			}
			
			catch (Exception e){
				throw new UncheckedFilesystemException("Error saving file",e);
			}
			
			if(url != null){
				path.setUrl(url);
				try {
					pathRep.addPath(path);
				}
				catch (Exception e){
					throw new UncheckedPersistenceException("Error adding path to database", e);
				}
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
