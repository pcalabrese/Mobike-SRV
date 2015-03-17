package Controller;

import javax.ws.rs.core.Response;

public interface ReviewServices {
		
		/**
		 * @param json
		 * @return String
		 */
		public Response createReview(String wrappingJson);
		
		/**
		 * @param id
		 * @return String
		 */
		public Response getReview(Long id);
		
		/**
		 * @return String
		 */
		public Response retrieveAllReviews();

		public Response removeReview(String cryptedJson);
		
	}
