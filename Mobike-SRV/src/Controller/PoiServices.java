package Controller;

import javax.ws.rs.core.Response;

public interface PoiServices {

	public Response createPoi(String cryptedJson);
	
	public Response deletePoi(String cryptedJson);
	
	public Response updatePoi(String cryptedJson);
	
	public Response getAllPois();

	Response createPoiList(String cryptedJson);

}
