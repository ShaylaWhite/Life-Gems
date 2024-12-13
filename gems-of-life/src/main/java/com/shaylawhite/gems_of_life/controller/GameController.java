package com.shaylawhite.gems_of_life.controller;

import com.shaylawhite.gems_of_life.exception.ApiException;
import com.shaylawhite.gems_of_life.model.Game;
import com.shaylawhite.gems_of_life.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    // Start a new game
    @PostMapping("/start")
    public ResponseEntity<Game> startGame() {
        Game game = gameService.startNewGame();
        return ResponseEntity.ok(game);
    }

    // Make a guess
    @PostMapping("/guess")
    public ResponseEntity<String> checkGuess(@RequestParam Long gameId, @RequestParam String guess) {
        if (guess == null || guess.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Guess cannot be empty.");
        }

        try {
            String feedback = gameService.checkGuess(gameId, guess);
            return ResponseEntity.ok(feedback);
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }


}
