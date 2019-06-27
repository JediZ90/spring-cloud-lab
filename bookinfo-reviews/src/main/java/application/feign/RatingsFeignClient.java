package application.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ratings", url = "ratings:9080")
public interface RatingsFeignClient {

	@GetMapping("/ratings/{productId}")
	Map<String, Object> ratings(@PathVariable int productId, @RequestHeader("end-user") String user,
			@RequestHeader("user-agent") String useragent, @RequestHeader("x-request-id") String xreq,
			@RequestHeader("x-b3-traceid") String xtraceid, @RequestHeader("x-b3-spanid") String xspanid,
			@RequestHeader("x-b3-parentspanid") String xparentspanid, @RequestHeader("x-b3-sampled") String xsampled,
			@RequestHeader("x-b3-flags") String xflags, @RequestHeader("x-ot-span-context") String xotspan);

}
