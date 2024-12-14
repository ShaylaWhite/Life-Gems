package com.shaylawhite.gems_of_life.controller;

import com.shaylawhite.gems_of_life.model.*;
import com.shaylawhite.gems_of_life.service.GameService;
import com.shaylawhite.gems_of_life.exception.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    // Start a new game
    @PostMapping("/start")
    public GameResponse startGame() {
        Game game = gameService.startNewGame();
        return new GameResponse("Game started!", game.getAttemptsLeft(), game.getCurrentLevel(), game.isWon(), game.getCurrentLifeLesson(), game.getGuessesHistory());
    }

    // Make a guess and get feedback
    @PostMapping("/guess")
    public GameResponse makeGuess(@RequestBody GuessRequest request) {
        try {
            String feedback = gameService.checkGuess(request.getGuess());
            Game currentGame = gameService.getCurrentGame();

            if (currentGame.isWon()) {
                feedback += " 💎 You've advanced to the next level: " + currentGame.getCurrentLifeLesson() + " 🌟";
            }

            return new GameResponse(feedback, currentGame.getAttemptsLeft(), currentGame.getCurrentLevel(), currentGame.isWon(), currentGame.getCurrentLifeLesson(), currentGame.getGuessesHistory());
        } catch (GameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Get current game status
    @GetMapping("/status")
    public GameResponse getStatus() {
        try {
            Game currentGame = gameService.getCurrentGame();
            return new GameResponse("Game Status", currentGame.getAttemptsLeft(), currentGame.getCurrentLevel(), currentGame.isWon(), currentGame.getCurrentLifeLesson(), currentGame.getGuessesHistory());
        } catch (GameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}





