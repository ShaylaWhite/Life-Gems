package com.shaylawhite.gems_of_life.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class GameService {

    private String secretCode;
    private int maxAttempts = 10;
    private int attemptsLeft;

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
        // Generate a random 4-digit code (you can modify this to fit your rules)
        return String.format("%04d", new Random().nextInt(10000));
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
