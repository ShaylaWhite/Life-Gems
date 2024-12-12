package com.shaylawhite.gems_of_life.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration                                  //Marks the class as a configuration class
public class AppConfig {
    @Bean                                       // Marks this method as defining a Spring-managed bean
    public RestTemplate restTemplate() {
        return new RestTemplate();              // Creates and returns a RestTemplate object
    }

}
// used for configuration purpose,methods that will define beans(objects) that spring will manage
//Spring automatically injects an instance of RestTemplate into your GameService when it is needed. This is done using dependency injection.