package com.example.tripbackend3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TripBackEnd3Application {

    public static void main(String[] args) {
        SpringApplication.run(TripBackEnd3Application.class, args);

    }

//    public static void main(String[] args) {
//        new SpringApplicationBuilder(TripBackEnd3Application.class)
//                .properties(APPLICATION_LOCATIONS)
//                .run(args);
//    }

}
