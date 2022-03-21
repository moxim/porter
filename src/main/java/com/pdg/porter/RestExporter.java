package com.pdg.porter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@SpringBootApplication
@RestController
public class RestExporter {
    private Integer count = 0;
    private Instant lastQuery = Instant.now();

    public static void main(String[] args) {
        System.out.println("1");
        SpringApplication.run(RestExporter.class, args);
    }

    @GetMapping("/kuckuck")
    public String kuckuck() {
        count++;
        lastQuery = Instant.now();
        return "Hi!";
    }

    @GetMapping("/count")
    public Integer count() {
        return count;
    }

    @GetMapping("/last")
    public Instant last() {
        return lastQuery;
    }
}
