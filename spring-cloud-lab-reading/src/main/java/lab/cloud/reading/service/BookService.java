//package lab.cloud.reading.service;
//
//import java.net.URI;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class BookService {
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    // @HystrixCommand(fallbackMethod = "reliable")
//    public String readingList() {
//        URI uri = URI.create("http://localhost:8002/recommended");
//
//        return this.restTemplate.getForObject(uri, String.class);
//    }
//
//    public String reliable() {
//        return "Cloud Native Java (O'Reilly)";
//    }
//
//}