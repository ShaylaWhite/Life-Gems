package com.shaylawhite.gems_of_life.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RandomGenerator {

    private static final String RANDOM_ORG_URL = "https://www.random.org/integers?num=4&min=0&max=7&col=1&base=10&format=plain";

    private final RestTemplate restTemplate;

    public RandomGenerator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Integer> generateRandomNumbers(int count) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format("%s?num=%d&min=0&max=9&col=1&base=10&format=plain&rnd=new", RANDOM_API_URL, count);
            String response = restTemplate.getForObject(url, String.class);

            return Arrays.stream(response.split("\n"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Fallback: generate numbers locally if the API fails
            System.out.println("API request failed, generating random numbers locally.");
            return generateRandomNumbersLocally(count);
        }
    }

    private List<Integer> generateRandomNumbersLocally(int count) {
        Random random = new Random();
        return random.ints(count, 0, 10)
                .boxed()
                .collect(Collectors.toList());
    }

}
