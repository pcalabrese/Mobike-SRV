package persistence;

import java.util.List;

import persistence.exception.PersistenceException;
import model.Poi;

public interface PoiRepository {

	public Poi poiFromId(long id) throws PersistenceException;
	
	public List<Poi> getAllPois() throws PersistenceException;
	
	public void removePoi(Poi poi) throws PersistenceException;
	
	public void removePoiFromId(long id) throws PersistenceException;
	
	public void addPoi(Poi poi) throws PersistenceException;
	
	public void updatePoi(Poi poi) throws PersistenceException;
	
	

}
