package com.shaylawhite.gems_of_life.service;

import com.shaylawhite.gems_of_life.model.Game;
import com.shaylawhite.gems_of_life.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// business logic
@Service
public class GameService {

    private static final String[] GEM_EMOJIS = {
            "💎", "❤️", "🌟", "🔥", "💠", "✨", "🔮", "🌙"
    };

    private final GameRepository gameRepository;
    private final RandomGenerator randomGenerator;

    @Autowired
    public GameService(GameRepository gameRepository, RandomGenerator randomGeneratorService) {
        this.gameRepository = gameRepository;
        this.randomGenerator = randomGeneratorService;
    }

    public String startNewGame() {
        String secretCode = generateSecretCode();
        int maxAttempts = 10;

        Game game = new Game();
        game.setSecretCombination(secretCode);  // Set the secretCombination as a string
        game.setRemainingGuesses(maxAttempts);
        game.setGameState("in-progress");

        gameRepository.save(game);

        return "Game started! Secret code is set. You have " + maxAttempts + " attempts.";
    }

    public String checkGuess(Long gameId, String guess) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // Input validation: Ensure guess is a valid number of the correct length
        if (guess.length() != game.getSecretCombination().size()) {
            return "Invalid guess. Please enter a guess of correct length.";
        }

        if (game.isGameOver()) {
            return "Game Over. Please start a new game.";
        }

        List<Integer> guessList = parseGuess(guess);
        String feedback = generateFeedback(game.getSecretCombination(), guessList);

        // Add guess and feedback to history
        game.addGuessHistory(guess, feedback);

        // Decrease remaining guesses and check for game over conditions
        game.decreaseRemainingGuesses();
        if (game.getRemainingGuesses() <= 0 || game.getSecretCombination().equals(guessList)) {
            game.setGameOver(true);
        }

        gameRepository.save(game);
        return feedback;
    }


    private String generateSecretCode() {
        List<Integer> randomNumbers = randomGenerator.generateRandomNumbers();
        System.out.println("Generated random numbers: " + randomNumbers);
        return randomNumbers.stream()
                .map(num -> GEM_EMOJIS[num])
                .collect(Collectors.joining());
    }

    private String provideFeedback(String guess, String secretCode) {
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
