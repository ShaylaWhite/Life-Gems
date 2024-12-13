package com.shaylawhite.gems_of_life.controller;

import com.shaylawhite.gems_of_life.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    //Endpoint to start a new game
    @PostMapping("/start")
    public String startNewGame() {
        return gameService.startNewGame();
    }

    //Endpoint to check the player's guess,extract the value of gameId from the URL and pass it as a parameter to the checkGuess method.
    @PostMapping("/guess/{gameId}")
    public String checkGuess(@RequestParam String guess,  @PathVariable Long gameId) {
        return gameService.checkGuess(guess, gameId);
    }
}
