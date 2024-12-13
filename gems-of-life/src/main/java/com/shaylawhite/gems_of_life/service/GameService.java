package com.shaylawhite.gems_of_life.service;

import com.shaylawhite.gems_of_life.model.Game;
import com.shaylawhite.gems_of_life.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public String checkGuess(String guess, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        if (game.getRemainingGuesses() == 0) {
            return "Game over. No attempts left!";
        }

        if (guess.equals(game.getSecretCombination())) {
            return "Congratulations! You've guessed the correct code!";
        }

        game.setRemainingGuesses(game.getRemainingGuesses() - 1);
        String feedback = provideFeedback(guess, game.getSecretCombination());
        gameRepository.save(game);

        return "Feedback: " + feedback + ". Attempts left: " + game.getRemainingGuesses();
    }

    private String generateSecretCode() {
        List<Integer> randomNumbers = randomGenerator.generateRandomNumbers();
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
