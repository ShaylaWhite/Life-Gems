package com.shaylawhite.gems_of_life.controller;

import com.shaylawhite.gems_of_life.model.Game;
import com.shaylawhite.gems_of_life.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<Game> startGame() {
        Game newGame = gameService.startNewGame();
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @PostMapping("/guess")
    public ResponseEntity<String> checkGuess(@RequestParam Long gameId, @RequestParam String guess) {
        if (guess == null || guess.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Guess cannot be empty.");
        }

        try {
            // Assuming this method returns feedback (String) after processing the guess.
            String feedback = gameService.checkGuess(gameId, guess);
            return ResponseEntity.ok(feedback);
        } catch (GameNotFoundException e) {
            // Custom exception handling for game not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
}
