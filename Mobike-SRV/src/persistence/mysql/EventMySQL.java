package persistence.mysql;
import java.util.ArrayList;
import java.util.List;
import model.Event;
import persistence.EventRepository;

import javax.persistence.*;

import persistence.jpa.SingletonEMF;


public class EventMySQL implements EventRepository{

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();
	
	public EventMySQL() {}
	
	
	@Override
	public Event eventFromId(long id){
		Event event = null;
		System.out.println(id);
		TypedQuery<Event> query = em.createQuery("select e from Event e where e.id=:id",Event.class);
		query.setParameter("id",id);
		event =query.getSingleResult();
		System.out.println(event.getName());
		return event;
	}




	@Override
	public List<Event> getAllEvents() {
		
		List<Event> events = new ArrayList<Event>();
		TypedQuery<Event> query = em.createQuery("select e from Event e",Event.class);
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
			insertedId = e.getid();
			
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
			Event event = em.find(Event.class,e.getid());
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



	


	