# Gem Guessing Game - ERD Overview

This document provides a breakdown of the Entity-Relationship Diagram (ERD) for the Gem Guessing Game. It outlines the key entities, their attributes, and the relationships between them.

## Entities

### 1. Game
Represents the game itself and its core attributes.

- **Attributes**:
  - `id`: Unique identifier for the game.
  - `secret_combination`: A string representing the randomly generated secret combination of numbers (e.g., '2 2 4 6').
  - `remaining_guesses`: Tracks the number of guesses left in the game.
  - `game_state`: Current state of the game (e.g., "Active", "Finished", "Ended").

### 2. Guess
Represents each guess made by the player.

- **Attributes**:
  - `id`: Unique identifier for the guess.
  - `guess_value`: The player's guess (e.g., '2 2 4 6').
  - `game_id`: Links the guess to a specific game.
  - `feedback_id`: Links the guess to the corresponding feedback for that guess.

### 3. Feedback
Stores feedback for a guess, such as the number of correct positions and correct gems.

- **Attributes**:
  - `id`: Unique identifier for feedback.
  - `correct_position`: Number of gems that are correct and in the correct position.
  - `correct_gems`: Number of gems that are correct but in the wrong position.
  - `guess_id`: Links the feedback to a specific guess.

### 4. LifeLesson
Provides motivational messages based on the number of correct gems.

- **Attributes**:
  - `id`: Unique identifier for the life lesson.
  - `lesson`: The life lesson message (e.g., "Every step forward, no matter how small, is growth").
  - `correct_gems`: Number of correct gems associated with the lesson.

## Relationships

- **Game ↔ Guess (1-to-many)**: A game can have multiple guesses, but each guess belongs to exactly one game.
- **Guess ↔ Feedback (1-to-1)**: Each guess has a single feedback record, which provides the result of that guess.
- **Feedback ↔ Guess (1-to-1)**: Each feedback is linked to a specific guess.
- **LifeLesson ↔ Feedback (1-to-many)**: A life lesson is associated with the number of correct gems, which will be displayed with the feedback.

## Visual Representation

Below is a simplified text representation of the ERD:


+---------------------+         +-------------------+         +-------------------+
|        Game         | 1     * |       Guess       | *     1 |    Feedback       |
+---------------------+         +-------------------+         +-------------------+
| - id (PK)           |         | - id (PK)         |         | - id (PK)         |
| - secret_combination |         | - guess_value     |         | - correct_position |
| - remaining_guesses  |         | - feedback_id (FK)|         | - correct_gems     |
| - game_state         |         | - game_id (FK)    |         +-------------------+
+---------------------+         +-------------------+
             |
             |
             | 1
             |
       +--------------------+
       |    LifeLesson      |
       +--------------------+
       | - id (PK)          |
       | - lesson           |
       | - correct_gems     |
       +--------------------+
Entity Relationship Flow:
Game Entity:

The game starts with a randomly generated secret_combination (e.g., '2 2 4 6') and sets the number of remaining_guesses.
The game_state will change based on the game progress, such as "Active", "Finished", or "Ended".
Guess Entity:

When a player submits a guess, a new Guess is created, containing the guess value and linked to a Game entity.
Each guess is associated with feedback (how many correct positions and gems), which is stored in the Feedback entity.
Feedback Entity:

The feedback provides two main pieces of information:
The number of correct_position gems (exact matches).
The number of correct_gems (correct gems in the wrong position).
The feedback is linked to the corresponding Guess record.
LifeLesson Entity:

After each guess, based on the number of correct gems, a corresponding LifeLesson is selected.
The LifeLesson contains a motivational message, offering encouragement for the player's progress.
