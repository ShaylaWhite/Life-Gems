package com.shaylawhite.gems_of_life.model;

import java.util.List;

public class GuessRequest {
    private List<Integer> guess;

    public List<Integer> getGuess() {
        return guess;
    }

    public void setGuess(List<Integer> guess) {
        this.guess = guess;
    }
}
