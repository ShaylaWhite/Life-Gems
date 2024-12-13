package com.shaylawhite.gems_of_life.service;

import com.shaylawhite.gems_of_life.model.Game;
import com.shaylawhite.gems_of_life.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private static final String[] GEM_EMOJIS = {
            "💎", "🪙", "🌟", "🔥", "💠", "✨", "🔮", "🌙"
    };

    private final GameRepository gameRepository;
    private final RandomGenerator randomGenerator;

    @Autowired
    public GameService(GameRepository gameRepository, RandomGenerator randomGenerator) {
        this.gameRepository = gameRepository;
        this.randomGenerator = randomGenerator;
    }

    // Start a new game and generate a secret combination.
    public Game startNewGame() {
        List<Integer> secretCombination = generateSecretCombination(); // Generate the combination as List<Integer>
        Game game = new Game(secretCombination); // Pass the List<Integer> to the constructor
        gameRepository.save(game);
        return game;
    }

    // Check player's guess against the secret combination
    public String checkGuess(Long gameId, String guessInput) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (game.isGameOver()) {
            return "Game Over. Please start a new game.";
        }

        // Validate the input format (4 numbers separated by spaces)
        if (!isValidGuess(guessInput)) {
            return "Invalid guess format. Please enter 4 numbers between 0-7 separated by spaces.";
        }

        List<Integer> guess = parseGuess(guessInput); // Convert input into a List<Integer>
        String feedback = provideFeedback(guess, game.getSecretCombination());

        // Determine the life lesson based on feedback
        String lifeLesson = determineLifeLesson(feedback);

        // Add guess, feedback, and life lesson to history
        game.addGuessHistory(guess, feedback, lifeLesson);

        // Decrease remaining guesses and check for game over conditions
        game.decreaseRemainingGuesses();
        if (game.getRemainingGuesses() <= 0 || guess.equals(game.getSecretCombination())) {
            game.setGameOver(true); // Set the game as over
        }

        gameRepository.save(game);

        // If game over, reveal the secret combination
        if (game.isGameOver()) {
            return guess.equals(game.getSecretCombination()) ?
                    "Congratulations! You guessed correctly! 🎉" :
                    "Game Over! The correct combination was: " + mapGuessToEmojis(game.getSecretCombination());
        }

        return feedback + "\nLife Lesson: " + lifeLesson;
    }

    // Helper method to generate the secret combination
    private List<Integer> generateSecretCombination() {
        return randomGenerator.generateRandomNumbers(4); // Use random generator to fetch 4 numbers
    }

    // Helper method to validate the guess format
    private boolean isValidGuess(String guessInput) {
        String[] guessParts = guessInput.split(" ");
        return guessParts.length == 4 && guessInput.matches("[0-7] [0-7] [0-7] [0-7]");
    }

    // Convert the input guess to a list of integers
    private List<Integer> parseGuess(String guessInput) {
        String[] guessParts = guessInput.split(" ");
        List<Integer> guess = new ArrayList<>();
        for (String part : guessParts) {
            guess.add(Integer.parseInt(part.trim()));
        }
        return guess;
    }

    // Generate feedback based on the guess and the secret combination
    private String provideFeedback(List<Integer> guess, List<Integer> secretCombination) {
        int correctPosition = 0;
        int correctGem = 0;

        for (int i = 0; i < secretCombination.size(); i++) {
            if (guess.get(i).equals(secretCombination.get(i))) {
                correctPosition++;
            } else if (secretCombination.contains(guess.get(i))) {
                correctGem++;
            }
        }

        return correctPosition + " correct positions, " + correctGem + " correct gems.";
    }

    // Generate a life lesson based on the number of correct positions in the guess
    private String determineLifeLesson(String feedback) {
        int correctPositions = Integer.parseInt(feedback.split(" ")[0]);

        switch (correctPositions) {
            case 0:
                return "Every step forward, no matter how small, is growth. Keep moving forward.";
            case 1:
                return "You are valuable beyond measure. Never let anyone make you forget that.";
            case 2:
                return "Perseverance is the key to success. Keep pushing forward!";
            case 3:
                return "Great things take time, and you are making progress every step of the way!";
            case 4:
                return "Congratulations, you unlocked the lesson of self-worth. You are worthy!";
            default:
                return "Keep learning and growing. Every step counts.";
        }
    }

    // Map the guessed numbers to their respective gem emojis
    private String mapGuessToEmojis(List<Integer> guess) {
        StringBuilder emojiGuess = new StringBuilder();
        for (Integer gem : guess) {
            emojiGuess.append(GEM_EMOJIS[gem]).append(" ");
        }
        return emojiGuess.toString().trim();
    }
}


