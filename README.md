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
