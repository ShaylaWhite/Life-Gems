let guessesLeft = 10;
let guessHistory = [];
let correctPattern = [];
let currentGame = null;

// Start a new game
function startGame() {
    fetch('http://localhost:8080/game/start', { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            currentGame = data; // Store the current game details
            guessesLeft = currentGame.attemptsLeft;
            updateUI('Game started! Make your first guess.');
            updateGuessesRemaining();
        })
        .catch(error => console.error('Error starting game:', error));
}

// Make a guess
function makeGuess(guess) {
    fetch('http://localhost:8080/game/guess', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ guess: guess })
    })
        .then(response => response.json())
        .then(data => {
            let feedback = data.feedback;
            guessesLeft = data.attemptsLeft;

            // Update the guess history and UI
            guessHistory.push({ guess, feedback });
            updateGuessHistory();
            updateGuessesRemaining();
            updateUI(feedback);

            // Check if the game is won or over
            if (data.isWon) {
                endGame(true);
            } else if (guessesLeft === 0) {
                endGame(false);
            }
        })
        .catch(error => console.error('Error making guess:', error));
}

// Get current game status
function getGameStatus() {
    fetch('http://localhost:8080/game/status')
        .then(response => response.json())
        .then(data => {
            currentGame = data;
            guessesLeft = currentGame.attemptsLeft;
            updateGuessesRemaining();
        })
        .catch(error => console.error('Error fetching game status:', error));
}

// Update the UI with feedback and guesses remaining
function updateUI(feedbackText) {
    document.getElementById('feedback-text').textContent = feedbackText;
    document.getElementById('gem').textContent = "Keep going, you're getting closer!";
}

// Update guess history in the UI
function updateGuessHistory() {
    let historyList = document.getElementById('guess-history');
    historyList.innerHTML = '';
    guessHistory.forEach(entry => {
        let listItem = document.createElement('li');
        listItem.textContent = `Guess: ${entry.guess.join(' ')} - Feedback: ${entry.feedback}`;
        historyList.appendChild(listItem);
    });
}

// Update guesses remaining in the UI
function updateGuessesRemaining() {
    document.getElementById('guesses-left').textContent = guessesLeft;
}

// End the game (win or lose)
function endGame(won) {
    if (won) {
        document.getElementById('feedback-text').textContent = "Congratulations, you've won the game!";
        document.getElementById('gem').textContent = "You did it! Your persistence paid off!";
    } else {
        document.getElementById('feedback-text').textContent = "Game Over! You've run out of attempts.";
        document.getElementById('gem').textContent = "Better luck next time!";
    }

    // Disable further guesses
    document.getElementById('submit-guess').disabled = true;
}

// Start the game when the page loads
startGame();

// Event listener for submitting guesses
document.getElementById('submit-guess').addEventListener('click', () => {
    let guessInput = document.getElementById('guess').value.trim();
    let guess = guessInput.split(' ').map(Number);

    // Validate the guess
    if (guess.length !== 4 || !guess.every(num => num >= 0 && num <= 7)) {
        alert("Please enter a valid guess with 4 numbers from 0 to 7.");
        return;
    }

    // Make the guess via the API
    makeGuess(guess);
});

