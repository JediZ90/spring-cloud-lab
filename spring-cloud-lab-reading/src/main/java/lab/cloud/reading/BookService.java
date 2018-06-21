package lab.cloud.reading;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService {

    private final RestTemplate restTemplate;

    public BookService(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String readingList() {
        URI uri = URI.create("http://localhost:8002/recommended");

        return this.restTemplate.getForObject(uri, String.class);
    }

    public String reliable() {
        return "Cloud Native Java (O'Reilly)";
    }
}