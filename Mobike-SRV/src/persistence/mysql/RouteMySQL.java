package persistence.mysql;

import java.util.ArrayList;
import java.util.List;
import model.Route;
import persistence.RouteRepository;
import javax.persistence.*;
import persistence.jpa.SingletonEMF;


public class RouteMySQL implements RouteRepository{

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();
	
	public RouteMySQL() {}
	
	
	@Override
	public Route routeFromId(long id){
		Route route = null;
		TypedQuery<Route> query = em.createQuery("select r from Route r where r.id=:id",Route.class);
		query.setParameter("id",id);
		route =query.getSingleResult();
		return route;
	}




	@Override
	public List<Route> getAllRoutes() {
		
		List<Route> routes = new ArrayList<Route>();
		TypedQuery<Route> query = em.createQuery("select r from Route r",Route.class);
		routes = query.getResultList();
		return routes;
	}




	@Override
	public Route routeFromName(String name) {
		
		Route route = null;
		Query query = em.createQuery("select r from Route r where r.name=:name");
		query.setParameter("name",name);
		route =(Route) query.getSingleResult();
		
		return route;
	}




	@Override
	public long addRoute(Route p) throws PersistenceException {
		long insertedId = -1;
		try{
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			insertedId = p.getId();
			
			}
		catch(IllegalArgumentException e){
			e.getStackTrace();
			}
		finally{
			
			em.close();
			}
		return insertedId;	
	}




	@Override
	public void removeRoute(Route p) {
		
		
		try{
			em.getTransaction().begin();
			Route route = em.find(Route.class,p.getId());
			if(route!=null && route.equals(p))
				em.remove(p);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e){
			e.getStackTrace();
		}
		
		finally{
			em.close();
		}
		
		
		
	}




	@Override
	public void removeRouteFromId(long id) {
		
		try{
			em.getTransaction().begin();
			Route p = em.find(Route.class,id);
			if(p!=null)
				em.remove(p);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e){
			e.getStackTrace();
		}
		
		finally{
			em.close();
		}
		
		
	}
	
}



	


	