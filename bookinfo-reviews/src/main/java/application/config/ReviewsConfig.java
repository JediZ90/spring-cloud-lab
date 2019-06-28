package application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "reviews")
@Component("reviewsConfig")
@RefreshScope
public class ReviewsConfig {

	private String starColor;

	public String getStarColor() {
		return starColor;
	}

	public void setStarColor(String starColor) {
		this.starColor = starColor;
	}
}
