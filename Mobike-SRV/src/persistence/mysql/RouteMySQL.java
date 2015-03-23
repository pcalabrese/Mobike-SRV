package persistence.mysql;

import java.util.List;
import model.Route;
import persistence.RouteRepository;
import javax.persistence.*;


import persistence.jpa.SingletonEMF;


public class RouteMySQL implements RouteRepository{

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();
	
	public RouteMySQL() {}
	
	/* metodo aggiornato,
	 * rimosso l'utilizzo della getSingleResult()
	 * in quanto se l'elemento non viene trovato nel DB solleva eccezione */
	
	@Override
	public Route routeFromId(long id){
		List<Route> results = null;
		Route route = null;
		TypedQuery<Route> query = em.createNamedQuery("Route.findById", Route.class);
		query.setParameter("id",id);
		results = query.getResultList();
		
		
		if(results != null && !(results.isEmpty())){
			route = results.get(0);
			
		}
		return route;
	
	}

	/* 
	 * metodo aggiornato,
	 * se non ci sono Routes nel db allora restituisce null.*/
	
	@Override
	public List<Route> getAllRoutes() {
		
		List<Route> routes = null;
		TypedQuery<Route> query = em.createNamedQuery("Route.findAll",Route.class);
		routes = query.getResultList();
		
		
		return routes;
	}


	/* metodo aggiornato
	 * restituisce la lista di Route corrispondenti a quel nome, 
	 * dato che la colonna Name non � Unique.*/
	
	@Override
	public List<Route> routeFromName(String name) {
		
		List<Route> results = null;
		TypedQuery<Route> query = em.createNamedQuery("Route.findbyName",Route.class);
		query.setParameter("name",name);
		results = query.getResultList();
		
		return results;
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
			throw new PersistenceException(e.getMessage());
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
			throw new PersistenceException(e.getMessage());
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
			throw new PersistenceException(e.getMessage());
		}
		
		finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Route> searchRouteByCriteria(String startLocation, String endLocation, int minLength, int maxLength, int minDuration, int maxDuration, String type){
		List<Route> routes = null;
		String stringSQL;
		stringSQL = "SELECT * FROM ROUTES WHERE 1";
		stringSQL += startLocation != null ?  " AND startlocation=\"" + startLocation + "\"" : "";
		stringSQL += endLocation != null ? " AND endlocation=\"" + endLocation + "\"" : "";
		stringSQL += minLength > 0.0 ? " AND length>" + minLength : "";
		stringSQL += maxLength > 0.0 ? " AND length<" + maxLength : "";
		stringSQL += minDuration > 0 ?  " AND duration>" + minDuration : "";
		stringSQL += maxDuration > 0 ? " AND duration<" + maxDuration : "";
		stringSQL += type!=null ? " AND type=\"" + type + "\"": "";
		
		Query query = em.createNativeQuery(stringSQL,Route.class);
		routes = query.getResultList();	
		
		return routes;
	}
	
	@Override
	public void updateRates(long id){
		em.getTransaction().begin();
		Route route = em.find(Route.class, id);
		route.calculateRate();
		
		em.getTransaction().commit();
	}
	
	@Override
	@OrderBy("uploaddate DESC")
	public List<Route> lastUploaded(){

		List<Route> routes = null;
		TypedQuery<Route> query = em.createNamedQuery("Route.findAll",Route.class);
		query.setMaxResults(6);
		routes = query.getResultList();
		
		
		return routes;
	}
	
}



	


	