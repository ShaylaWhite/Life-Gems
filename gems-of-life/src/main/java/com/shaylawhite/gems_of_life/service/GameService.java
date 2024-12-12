package com.shaylawhite.gems_of_life.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that contains the logic for the game, such as starting a new game, checking guesses,
 * and generating the secret code. It uses a Random Number Generator API to generate the secret code
 * and provides feedback on the player's guesses.
 */
@Service
public class GameService {

    private String secretCode;
    private int maxAttempts = 10;
    private int attemptsLeft;

    @Autowired
    private RestTemplate restTemplate; // RestTemplate to make a call to the Random Number Generator API.

    private static final String[] GEM_EMOJIS = {
            "💎", "❤️", "🌟", "🔥", "💠", "✨", "🔮", "🌙"
    };

    private static final String RANDOM_ORG_URL = "https://www.random.org/integers?num=4&min=0&max=7&col=1&base=10&format=plain";

    /**
     * Starts a new game by generating a secret code and initializing attempts.
     *
     * @return A message indicating that the game has started, with the number of attempts left.
     */
    public String startNewGame() {
        secretCode = generateSecretCode();  // Generate a new secret code.
        attemptsLeft = maxAttempts;  // Reset attempts to the maximum.
        return "Game started! Secret code is set. You have " + attemptsLeft + " attempts.";
    }

    /**
     * Checks the player's guess against the secret code.
     * Provides feedback on the guess and decrements the remaining attempts.
     *
     * @param guess The player's guess for the secret code.
     * @return A message indicating the result of the guess and the number of attempts left.
     */
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

    /**
     * Generates a secret code by calling an external API for random numbers and mapping them to gem emojis.
     *
     * @return The generated secret code as a string of gem emojis.
     */
    private String generateSecretCode() {
        List<Integer> randomNumbers = getRandomNumbersFromAPI();
        return randomNumbers.stream()
                .map(num -> GEM_EMOJIS[num])  // Map each random number to a gem emoji.
                .collect(Collectors.joining());  // Join them into a single string.
    }

    /**
     * Makes a request to an external API to fetch random numbers for the game.
     *
     * @return A list of integers representing the random numbers fetched from the API.
     */
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

    /**
     * Provides feedback on the player's guess by comparing it to the secret code.
     *
     * @param guess The player's guess for the secret code.
     * @return A feedback string indicating the correctness of the guess.
     */
    private String provideFeedback(String guess) {
        StringBuilder feedback = new StringBuilder();

        // Loop through each character in the guess and compare with the secret code
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
