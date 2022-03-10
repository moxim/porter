package com.pdg.porter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestExporter {
    public static void main(String[] args) {
        System.out.println("1");
        SpringApplication.run(RestExporter.class, args);
    }

    @GetMapping("/kuckuck")
    public String answer() {
        return "Hi!";
    }
}
