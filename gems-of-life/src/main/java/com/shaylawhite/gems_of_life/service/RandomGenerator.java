package com.shaylawhite.gems_of_life.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class RandomGenerator {

    private RestTemplate restTemplate = new RestTemplate();
    private static final String RANDOM_API_URL = "https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new";  // Fetch 4 numbers between 0 and 7

    // Method to generate random numbers
    public List<Integer> generateRandomNumbersFromApi() {
        int attempts = 0;
        boolean success = false;
        List<Integer> randomNumbers = new ArrayList<>();

        while (attempts < 5 && !success) {
            try {
                // Sending GET request
                ResponseEntity<String> response = restTemplate.exchange(
                        RANDOM_API_URL, HttpMethod.GET, null, String.class);

                // Log the redirect location (if any)
                HttpHeaders headers = response.getHeaders();
                String location = headers.getLocation() != null ? headers.getLocation().toString() : "No redirect";
                System.out.println("Redirect Location: " + location);

                // Parse and return the random numbers from the response
                randomNumbers = parseRandomNumbers(response.getBody());
                success = true;
            } catch (HttpServerErrorException.ServiceUnavailable e) {
                attempts++;
                if (attempts < 5) {
                    System.out.println("Server too busy. Retrying...");
                    try {
                        TimeUnit.SECONDS.sleep(2 * attempts); // Exponential backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.out.println("Max retries reached.");
                }
            } catch (HttpClientErrorException.Unauthorized e) {
                System.out.println("Unauthorized access. Check authentication.");
                break;
            } catch (ResourceAccessException e) {
                System.out.println("Network error occurred: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
                break;
            }
        }
        return randomNumbers;
    }

    // Method to parse the response and extract numbers
    private List<Integer> parseRandomNumbers(String response) {
        List<Integer> numbers = new ArrayList<>();
        String[] numbersStr = response.split("\n");
        for (String num : numbersStr) {
            try {
                numbers.add(Integer.parseInt(num.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid number: " + num);
            }
        }
        return numbers;
    }
}

