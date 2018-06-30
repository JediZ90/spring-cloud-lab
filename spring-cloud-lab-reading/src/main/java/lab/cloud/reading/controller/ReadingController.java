package lab.cloud.reading.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ReadingController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/to-read")
    public String readingList() {
        URI uri = URI.create("http://spring-cloud-lab-bookstore/recommended");
        return restTemplate.getForObject(uri, String.class);
    }
}
