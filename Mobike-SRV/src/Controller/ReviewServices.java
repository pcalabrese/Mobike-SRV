package Controller;

import javax.ws.rs.core.Response;

public interface ReviewServices {

	public Response createReview(String cryptedJson);
	
	public Response updateReview(String cryptedJson);
	
	public Response removeReview(String cryptedJson);

}
