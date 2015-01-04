package Controller;

import java.util.List;

import persistence.RouteRepository;
import persistence.exception.*;
import persistence.mysql.RouteMySQL;
import persistence.fs.*;
import model.Route;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;


@Path("/routes")
	public class ControllerMainImpl implements ControllerMain {
		protected RouteRepository routeRep;
		public ControllerMainImpl(){
			routeRep = new RouteMySQL();
		}
		
		@POST
		@Path("/create")
		@Consumes(MediaType.APPLICATION_JSON)
		public void createRoute(String json){
				Gson gson = new GsonBuilder().create();
				Route r = gson.fromJson(json, Route.class);
				String gpxString = r.getGpxString();
				System.out.println(r.getName());
				String url = null;
				
			
				
			try {
				RouteWriter writer = new GpxWriter();
				url = writer.write(gpxString, r.getName());
			}
			
			catch (Exception e){
				throw new UncheckedFilesystemException("Error saving file to filesystem",e);
			}
			
			if(url != null){
				r.setUrl(url);
				try {
					routeRep.addRoute(r);
				}
				catch (Exception e){
					throw new UncheckedPersistenceException("Error adding route to database", e);
				}
			}
			
		}
		

		@Override
		public Route searchRoute(Long id) {
			Route route = null;
			RouteRepository routeREP = new RouteMySQL();
			try{
			route = routeREP.routeFromId(id);
			}
			catch(PersistenceException pex){}
			return route;
		}

		@Override
		public List<Route> retriveAllRoutes() {
			// TODO Auto-generated method stub
			return null;
		}
}
