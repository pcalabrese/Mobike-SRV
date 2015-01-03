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
	
	
	
	
	public Route routeFromId(long id){
		
		Route route = null;
		Query query = em.createQuery("select p from Route p where p.Id=:id");
		query.setParameter("id",id);
		route =(Route) query.getSingleResult();
		
		return route;
		
	}




	@Override
	public List<Route> allRoute() {
		
		List<Route> routes = new ArrayList<Route>();
		Query query = em.createQuery("select p from Route p");
		routes =query.getResultList();
		return routes;
	}




	@Override
	public Route routeFromName(String name) {
		
		Route route = null;
		Query query = em.createQuery("select p from Route p where p.nome=:nome");
		query.setParameter("name",name);
		route =(Route) query.getSingleResult();
		
		return route;
	}




	@Override
	public void addRoute(Route p) throws PersistenceException {
		try{
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e){
			e.getStackTrace();
		}
		finally{
			em.close();
		}
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



	


	