package com.shaylawhite.gems_of_life.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RandomGenerator {

    private static final String RANDOM_ORG_URL = "https://www.random.org/integers?num=4&min=0&max=7&col=1&base=10&format=plain";

    private final RestTemplate restTemplate;

    public RandomGenerator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Integer> generateRandomNumbers() {
        String response = restTemplate.getForObject(RANDOM_ORG_URL, String.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Failed to fetch random numbers from the API");
        }

        return response.lines()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
