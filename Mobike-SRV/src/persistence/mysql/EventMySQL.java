package persistence.mysql;


import java.util.List;
import model.Event;
import persistence.EventRepository;
import javax.persistence.*;
import persistence.jpa.SingletonEMF;


public class EventMySQL implements EventRepository{

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();
	
	//costruttore senza parametri 
	
	public EventMySQL() {}
	
	/* metodo aggiornato,
	 * rimosso l'utilizzo della getSingleResult()
	 * in quanto se l'elemento non viene trovato nel DB solleva eccezione */
	
	@Override
	public Event eventFromId(long id){
		List<Event> results = null;
		Event event = null;
		TypedQuery<Event> query = em.createNamedQuery("Event.findById",Event.class);
		query.setParameter("id",id);
		
		results = query.getResultList();
		
		if(results==null){
			return event;
		}
		else {
			if(!(results.isEmpty()))
				return event = results.get(0);
			else
				return event;
		}
	}

	/* 
	 * metodo aggiornato,
	 * se non ci sono Routes nel db allora restituisce null.*/

	
	
	@Override
	public List<Event> getAllEvents() {
		
		List<Event> events = null;
		TypedQuery<Event> query = em.createNamedQuery("Event.findAll",Event.class);
		events = query.getResultList();
		
		return events;
	}
	

	@Override
	public long addEvent(Event e) throws PersistenceException {
		long insertedId = -1;
		try{
			em.getTransaction().begin();
			em.persist(e);
			em.getTransaction().commit();
			insertedId = e.getId();
			
			}
		catch(IllegalArgumentException e1){
			e1.getStackTrace();
			}
		finally{
			
			em.close();
			}
		return insertedId;	
	}




	@Override
	public void removeEvent(Event e) {
		
		
		try{
			em.getTransaction().begin();
			Event event = em.find(Event.class,e.getId());
			if(event!=null && event.equals(e))
				em.remove(e);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e1){
			e1.getStackTrace();
		}
		
		finally{
			em.close();
		}
		
		
		
	}




	@Override
	public void removeEventFromId(long id) {
		
		try{
			em.getTransaction().begin();
			Event e = em.find(Event.class,id);
			if(e!=null)
				em.remove(e);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e1){
			e1.getStackTrace();
		}
		
		finally{
			em.close();
		}
		
		
	}
	
}



	


	