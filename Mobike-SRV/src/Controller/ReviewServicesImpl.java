package Controller;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import persistence.mysql.ReviewMySQL;

/**
 * RESTful Review Endpoint
 * 
 * @author Paolo, Bruno, Marco, Andrea
 * @version 3.0
 * @see ReviewServices ReviewServices: Interface implemented by this class
 * @see ReviewMySQL ReviewMySQL: SQL DAO Class
 *
 */
@Path("/reviews")
public class ReviewServicesImpl implements ReviewServices {

	@Override
	public Response createReview(String wrappingJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getReview(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response retrieveAllReviews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response removeReview(String cryptedJson) {
		// TODO Auto-generated method stub
		return null;
	}

}
