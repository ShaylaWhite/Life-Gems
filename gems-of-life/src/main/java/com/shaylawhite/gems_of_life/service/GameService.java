package com.shaylawhite.gems_of_life.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class GameService {

    private String secretCode;
    private int maxAttempts = 10;
    private int attemptsLeft;

    @Autowired
    private RestTemplate restTemplate;

    private static final String[] GEM_EMOJIS = {
            "💎", "❤️", "🌟", "🔥", "💠", "✨", "🔮", "🌙"
    };

    private static final String RANDOM_ORG_URL = "https://www.random.org/integers?num=4&min=0&max=7&col=1&base=10&format=plain";

    public String startNewGame() {
        secretCode = generateSecretCode();
        attemptsLeft = maxAttempts;
        return "Game started! Secret code is set. You have " + attemptsLeft + " attempts.";
    }

    public String checkGuess(String guess) {
        if (attemptsLeft == 0) {
            return "Game over. No attempts left!";
        }

        if (guess.equals(secretCode)) {
            return "Congratulations! You've guessed the correct code!";
        }

        attemptsLeft--;
        String feedback = provideFeedback(guess);
        return "Feedback: " + feedback + ". Attempts left: " + attemptsLeft;
    }

    private String generateSecretCode() {
        // Call the Random Number Generator API
        List<Integer> randomNumbers = getRandomNumbersFromAPI();
        return randomNumbers.stream()
                .map(num -> GEM_EMOJIS[num])  // Map each random number to a gem emoji
                .collect(Collectors.joining());
    }

    private List<Integer> getRandomNumbersFromAPI() {
        String response = restTemplate.getForObject(RANDOM_ORG_URL, String.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Failed to fetch random numbers from the API");
        }
        // Split the response into integers and collect them into a list
        return response.lines()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private String provideFeedback(String guess) {
        // Simple feedback based on correct/incorrect positions
        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < secretCode.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                feedback.append("Correct ");
            } else if (secretCode.contains(String.valueOf(guess.charAt(i)))) {
                feedback.append("Wrong position ");
            } else {
                feedback.append("Incorrect ");
            }
        }
        return feedback.toString();
    }
}
