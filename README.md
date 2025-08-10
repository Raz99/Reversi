# Reversi

An extended version of the classic **Reversi (Othello)** board game, implemented in **Java** with a graphical user interface (GUI), special disc types and AI opponents.  
Originally developed as part of an **Object-Oriented Programming** university project.

---

## Quick Start

[📥 Download the JAR file](https://github.com/Raz99/Reversi/releases/download/V1.0.0/Reversi.jar)

Simply download and run the JAR to start playing!

```bash
java -jar Reversi.jar
```

---

## Overview

Reversi is a strategy board game played on an 8×8 grid.  
The goal is to finish the game with the majority of discs in your color.

This version introduces:
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
    - Cannot flip an **Unflippable Disc**.

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

The game uses a provided **ReversiGUI** framework:
- Displays the board and discs.
- Allows placing discs by clicking on squares.
- Shows messages for moves, flips, undos, and the winner.

---

## Project Structure

```
src/
  AIPlayer.java
  BombDisc.java
  Disc.java
  GameLogic.java
  GreedyAI.java
  HumanPlayer.java
  Main.java
  Move.java
  PlayableLogic.java
  Player.java
  Position.java
  RandomAI.java
  ReversiGUI.java
  SimpleDisc.java
  UnflippableDisc.java
```
---

## License
This project is licensed under the MIT License – see the LICENSE file for details.

---