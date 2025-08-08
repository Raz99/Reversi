# Reversi

An extended version of the classic **Reversi (Othello)** board game, implemented in Java with a graphical user interface (GUI), special disc types, and AI opponents.  
This project was developed as part of a university Object-Oriented Programming course.

---

## Overview

Reversi is a strategy board game played on an 8×8 grid.  
The goal is to finish the game with the majority of discs in your color.

This **extended version** introduces:
- **Special disc types** with unique behaviors.
- **AI players** with different strategies.
- **Undo** and **Reset** functionality.

---

## Game Rules

### Objective
Finish the game with the highest number of discs in your color.

### Starting Position
- The game starts with 4 discs in the center of the board:
    - Player 1 (Blue) – positions (3,3) and (4,4)
    - Player 2 (Red) – positions (3,4) and (4,3)

---

### Disc Types

1. **⬤ Simple Disc**
    - Standard Reversi disc.
    - Flips according to standard rules.

2. **⭕ Unflippable Disc**
    - Cannot be flipped after being placed.
    - Any opponent discs in the same line are flipped, but this disc remains.

3. **💣 Bomb Disc**
    - When flipped, all adjacent discs (including diagonals) are flipped.
    - May trigger chain reactions with other bomb discs.
    - Cannot flip an **UnflippableDisc**.

---

### Game Flow

- Players take turns placing a disc of their color on an empty square.
- The move must flip at least one opponent disc.
- Discs are flipped if they lie in a straight line (horizontal, vertical, diagonal) between the new disc and another disc of the current player's color.
- The game ends when no legal moves are available for either player.
- The player with the most discs wins.

---

### Undo & Reset

- **Undo**:
    - Available only in a 2-human-player game (no AI).
    - Can undo multiple moves back to the initial position.
- **Reset**:
    - Restarts the game while keeping the win counters.

---

## AI Players

- **RandomAI** – Chooses a random legal move.
- **GreedyAI** – Chooses the move that flips the most opponent discs.  
  Tie-breaking: rightmost position, then bottommost.

---

## GUI

The game uses a provided **GUI_for_chess_like_games** framework:
- Displays the board and discs.
- Allows placing discs by clicking on squares.
- Shows messages for moves, flips, undos, and the winner.

---

## Project Structure

```
src/
  game/
    GameLogic.java         # Core game logic and rules
    SimpleDisc.java        # Standard disc behavior
    UnflippableDisc.java   # Unflippable disc behavior
    BombDisc.java          # Bomb disc behavior
    Position.java          # Represents a board position
    Move.java              # Represents a move (used for Undo)
    RandomAI.java          # Random AI implementation
    GreedyAI.java          # Greedy AI implementation

lib/
  provided/
    Main.java              # Entry point
    GUI_for_chess_like_games.java
    PlayableLogic.java
    Player.java
    AIPlayer.java
    Disc.java

assets/
  (Optional images/icons)

docs/
  Reversi_Spec.pdf         # Original assignment specification
```

---

## Running the Game

### Using IntelliJ IDEA
1. Open the project in IntelliJ.
2. Mark `src` and `lib` as **Source Folders**.
3. Run `provided.Main`.

### From Command Line
```bash
javac -d bin src/game/*.java lib/provided/*.java
java -cp bin provided.Main
```

---

## Creating a Runnable JAR

### IntelliJ (recommended)
1. `File → Project Structure → Artifacts`
2. `+ → JAR → From modules with dependencies`
3. Select `provided.Main` as Main Class.
4. Set output directory to `dist/`.
5. `Build → Build Artifacts → Build`.

### Command Line
```bash
javac -d bin src/game/*.java lib/provided/*.java
echo Main-Class: provided.Main > manifest.txt
jar cfm dist/ReversiExtended.jar manifest.txt -C bin .
java -jar dist/ReversiExtended.jar
```

---

## Example Console Output

```
Player 1 placed a ⬤ in (2, 4)
Player 1 flipped the ⬤ in (3, 4)

Player 2 placed a 💣 in (5, 5)
Player 2 flipped the ⬤ in (4, 5)
```

---

## Winning Announcement

At the end of the game:
```
Player 1 wins with 43 discs! Player 2 had 21 discs.
```
Or:
```
It's a tie! Both players have 32 discs.
```

---

## License
This project is licensed under the MIT License – see the LICENSE file for details.

---