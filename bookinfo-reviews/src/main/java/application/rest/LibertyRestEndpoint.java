package application.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.feign.RatingsFeignClient;

@RestController
public class LibertyRestEndpoint extends Application {

	private final static Boolean ratings_enabled = Boolean.valueOf(System.getenv("ENABLE_RATINGS"));
	private final static String star_color = System.getenv("STAR_COLOR") == null ? "black"
			: System.getenv("STAR_COLOR");

	@Autowired
	private RatingsFeignClient ratingsFeignClient;

	private Map<String, Object> getResponse(String productId, int starsReviewer1, int starsReviewer2) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("id", productId);

		Map<String, Object> reviewer1 = new HashMap<String, Object>();
		reviewer1.put("reviewer", "Reviewer1");
		reviewer1.put("text", "An extremely entertaining play by Shakespeare. The slapstick humour is refreshing!");
		if (ratings_enabled) {
			Map<String, Object> rating = new HashMap<String, Object>();
			if (starsReviewer1 != -1) {
				rating.put("stars", starsReviewer1);
				rating.put("color", star_color);
			} else {
				rating.put("error", "Ratings service is currently unavailable");
			}
			reviewer1.put("rating", rating);
		}

		Map<String, Object> reviewer2 = new HashMap<String, Object>();
		reviewer2.put("reviewer", "Reviewer2");
		reviewer2.put("text",
				"Absolutely fun and entertaining. The play lacks thematic depth when compared to other plays by Shakespeare.");
		if (ratings_enabled) {
			Map<String, Object> rating = new HashMap<String, Object>();
			if (starsReviewer2 != -1) {
				rating.put("stars", starsReviewer2);
				rating.put("color", star_color);
			} else {
				rating.put("error", "Ratings service is currently unavailable");
			}
			reviewer2.put("rating", rating);
		}

		List<Map<String, Object>> reviews = new ArrayList<Map<String, Object>>();
		reviews.add(reviewer1);
		reviews.add(reviewer2);

		response.put("reviews", reviews);

		return response;
	}

	@RequestMapping("/health")
	public Map<String, Object> health() {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "Reviews is healthy");

		return response;
	}

	@RequestMapping("/reviews/{productId}")
	public Map<String, Object> bookReviewsById(@PathVariable int productId,
			@RequestHeader(name = "end-user", required = false) String user,
			@RequestHeader(name = "user-agent", required = false) String useragent,
			@RequestHeader(name = "x-request-id", required = false) String xreq,
			@RequestHeader(name = "x-b3-traceid", required = false) String xtraceid,
			@RequestHeader(name = "x-b3-spanid", required = false) String xspanid,
			@RequestHeader(name = "x-b3-parentspanid", required = false) String xparentspanid,
			@RequestHeader(name = "x-b3-sampled", required = false) String xsampled,
			@RequestHeader(name = "x-b3-flags", required = false) String xflags,
			@RequestHeader(name = "x-ot-span-context", required = false) String xotspan) {
		int starsReviewer1 = -1;
		int starsReviewer2 = -1;

		if (ratings_enabled) {
			Map<String, Object> ratingsResponse = ratingsFeignClient.ratings(productId, user, useragent, xreq, xtraceid,
					xspanid, xparentspanid, xsampled, xflags, xotspan);

			if (ratingsResponse != null) {
				if (ratingsResponse.containsKey("ratings")) {
					Map<String, Object> ratings = (Map<String, Object>) ratingsResponse.get("ratings");
					if (ratings.containsKey("Reviewer1")) {
						starsReviewer1 = (Integer) ratings.get("Reviewer1");
					}
					if (ratings.containsKey("Reviewer2")) {
						starsReviewer2 = (Integer) ratings.get("Reviewer2");
					}
				}
			}
		}

		return getResponse(Integer.toString(productId), starsReviewer1, starsReviewer2);
	}
}
