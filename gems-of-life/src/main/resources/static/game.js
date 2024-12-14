document.getElementById("guess-button").addEventListener("click", function() {
    const guessInput = document.getElementById("guess-input").value;
    if (guessInput.length === 4) {
        makeGuess(guessInput);
    } else {
        alert("Please enter a 4-digit guess.");
    }
});

async function makeGuess(playerGuess) {
    try {
        const response = await fetch("http://localhost:8080/game/guess", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(playerGuess.split('').map(Number))  // Convert to array of integers
        });

        const feedback = await response.text();
        document.getElementById("feedback").innerText = feedback;
        updateStatus();
        addToHistory(playerGuess, feedback);
    } catch (error) {
        console.error("Error making guess:", error);
    }
}

async function updateStatus() {
    try {
        const response = await fetch("http://localhost:8080/game/status");
        const gameStatus = await response.json();
        if (gameStatus) {
            document.getElementById("remaining-guesses").innerText = gameStatus.remainingGuesses;
            document.getElementById("current-life-lesson").innerText = gameStatus.currentLifeLesson;
            if (gameStatus.remainingGuesses <= 0) {
                document.getElementById("restart-button").style.display = "inline";
            }
        }
    } catch (error) {
        console.error("Error fetching game status:", error);
    }
}

function addToHistory(guess, feedback) {
    const historyList = document.getElementById("history-list");
    const historyItem = document.createElement("li");
    historyItem.textContent = `Guess: ${guess} - Feedback: ${feedback}`;
    historyList.appendChild(historyItem);
}

