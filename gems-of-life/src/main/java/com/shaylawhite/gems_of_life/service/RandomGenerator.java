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
            String url = String.format("%s?num=%d&min=0&max=7&col=1&base=10&format=plain", RANDOM_ORG_URL, count);
            String response = restTemplate.getForObject(url, String.class);

            return Arrays.stream(response.split("\n"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("API request failed, generating random numbers locally.");
            return generateRandomNumbersLocally(count);
        }
    }

    private List<Integer> generateRandomNumbersLocally(int count) {
        Random random = new Random();
        return random.ints(count, 0, 8)  // Changed the max to 8 to match GEM_EMOJIS array size.
                .boxed()
                .collect(Collectors.toList());
    }
}
