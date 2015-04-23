package persistence.mysql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Poi;
import persistence.PoiRepository;
import persistence.jpa.SingletonEMF;

public class PoiMySQL implements PoiRepository {
	
	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();

	public PoiMySQL() {
	}

	@Override
	public Poi poiFromId(long id) throws PersistenceException{
		em.getTransaction().begin();
		Poi poi = null;
		poi = em.find(Poi.class, id);
		em.getTransaction().commit();
		return poi;
	}

	@Override
	public List<Poi> getAllPois() throws PersistenceException{
		List<Poi> poisList = null;
		TypedQuery<Poi> query = em.createNamedQuery("Poi.findAll", Poi.class);
		poisList = query.getResultList();
		return poisList;
	}

	@Override
	public void removePoi(Poi poi) throws PersistenceException{
		em.getTransaction().begin();
		em.remove(poi);
		em.getTransaction().commit();
		em.close();
		

	}

	@Override
	public void addPoi(Poi poi) throws PersistenceException{
		em.getTransaction().begin();
		em.persist(poi);
		em.getTransaction().commit();
		//em.close();
	}

	@Override
	public void updatePoi(Poi poi) throws PersistenceException{
		em.getTransaction().begin();
		Poi point = em.find(Poi.class, poi.getId());

		point.setTitle(poi.getTitle());
		point.setType(poi.getType());
		
		em.getTransaction().commit();
		em.close();

	}

	@Override
	public void removePoiFromId(long id) throws PersistenceException{
		em.getTransaction().begin();
		Poi poi = null;
		poi = em.find(Poi.class, id);
		em.remove(poi);
		em.getTransaction().commit();
		em.close();
		
	}

}
