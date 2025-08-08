import java.util.*;

/**
 * Represents the game logic.
 */
public class GameLogic implements PlayableLogic {
    private Disc[][] board;
    private final int boardSize = 8; // 8x8 board
    private Player player1;
    private Player player2;
    private boolean turnOfFirstPlayer;
    private Player currentPlayer;
    private Player opponentPlayer;
    private Stack<Move> moveHistory; // Stack to store the move history

    /**
     * Constructs the game logic.
     */
    public GameLogic() {
        this.board = new Disc[this.boardSize][this.boardSize];
        this.moveHistory = new Stack<>(); // Initialize the move history stack
    }

    /**
     * Places a disc on the board at the specified position.
     *
     * @param a    the position to place the disc.
     * @param disc the disc to place.
     * @return true if the disc was placed successfully, false otherwise.
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (isValidMove(a) && freeSpecialDisc(disc)) {
            Set<String> positionsToFlip = new HashSet<>();
            this.board[a.row()][a.col()] = disc; // Place disc on the board
            if (disc.getType().equals("⭕")) currentPlayer.reduce_unflippedable();
            if (disc.getType().equals("💣")) currentPlayer.reduce_bomb();
            int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

            for (int[] direction : directions) {
                int currentRow = a.row() + direction[0];
                int currentCol = a.col() + direction[1];
                Set<String> discsInThisDirection = new HashSet<>();

                while (currentRow >= 0 && currentRow < getBoardSize() && currentCol >= 0 && currentCol < getBoardSize()) {
                    Disc currentDisc = this.board[currentRow][currentCol];
                    String discPosition = currentRow + "," + currentCol; // Create a unique position key

                    if (currentDisc == null) {
                        discsInThisDirection.clear();
                        break;
                    }

                    if (currentDisc.getOwner() == this.currentPlayer) {
                        positionsToFlip.addAll(discsInThisDirection);
                        break;
                    }

                    if (currentDisc.getOwner() == this.opponentPlayer) {
                        if (!currentDisc.getType().equals("⭕")) { // If the disc is not unflippable
                            discsInThisDirection.add(discPosition);
                            if (currentDisc.getType().equals("💣")) {
                                appendBombDiscs(directions, currentRow, currentCol, discsInThisDirection);
                            }
                        }
                        currentRow += direction[0];
                        currentCol += direction[1];
                    }
                }
            }

            // Flip the discs
            for (String str : positionsToFlip) {
                String[] position = str.split(",");
                int row = Integer.parseInt(position[0]), col = Integer.parseInt(position[1]);
                board[row][col].setOwner(this.currentPlayer);
            }

            // Save the move and the flipped positions to the history stack
            moveHistory.push(new Move(a, disc, new ArrayList<>(positionsToFlip)));

            this.turnOfFirstPlayer = !this.turnOfFirstPlayer; // Switch the turns
            updatePlayers(); // Update the current and opponent player
            System.out.println("Player " + getCurrentPlayerNum() + " placed a " + disc.getType() + " in (" + a.row() + ", " + a.col() + ")");
            for (String positionStr : positionsToFlip) {
                String[] position = positionStr.split(",");
                int row = Integer.parseInt(position[0]), col = Integer.parseInt(position[1]);
                Disc discToFlip = this.board[row][col];
                System.out.println("Player " + getCurrentPlayerNum() + " flipped the " + discToFlip.getType() + " in (" + row + ", " + col + ")");
            }
            System.out.println();
            return true;
        }

        return false;
    }

    /**
     * Gets the disc at the specified position.
     *
     * @param position the position to get the disc from.
     * @return the disc at the specified position.
     */
    @Override
    public Disc getDiscAtPosition(Position position) {
        return this.board[position.row()][position.col()];
    }

    /**
     * Gets the size of the board.
     *
     * @return the size of the board.
     */
    @Override
    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Gets a list of valid moves.
     *
     * @return a list of valid moves.
     */
    @Override
    public List<Position> ValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                Position position = new Position(row, col);
                if (isValidMove(position)) {
                    validMoves.add(position);
                }
            }
        }
        return validMoves;
    }

    /**
     * Counts the number of discs that would be flipped if a disc is placed at the specified position.
     *
     * @param a the position to place the disc.
     * @return the number of discs that would be flipped.
     */
    @Override
    public int countFlips(Position a) {
        if (this.board[a.row()][a.col()] != null)
            return 0;

        int row = a.row();
        int col = a.col();
        Set<String> visitedPositions = new HashSet<>();
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        /*
        (-1, -1) (-1, 0) (-1, 1)  // top-left, top, top-right
        (0, -1)           (0, 1)    // left, right
        (1, -1)  (1, 0)  (1, 1)     // bottom-left, bottom, bottom-right
         */

        updatePlayers();

        for (int[] direction : directions) {
            Set<String> discsInThisDirection = new HashSet<>();
            int currentRow = row + direction[0];
            int currentCol = col + direction[1];

            while (currentRow >= 0 && currentRow < getBoardSize() && currentCol >= 0 && currentCol < getBoardSize()) {
                Disc currentDisc = this.board[currentRow][currentCol];
                String positionKey = currentRow + "," + currentCol; // row,col (ex: 3,4)

                if (currentDisc == null) {
                    discsInThisDirection.clear();
                    break;
                }

                if (currentDisc.getOwner() == this.currentPlayer) {
                    visitedPositions.addAll(discsInThisDirection);
                    break;
                }

                if (currentDisc.getOwner() == this.opponentPlayer) {
                    if (!currentDisc.getType().equals("⭕")) { // If the disc is not unflippable
                        discsInThisDirection.add(positionKey); // Mark as visited

                        if (currentDisc.getType().equals("💣")) {
                            appendBombDiscs(directions, currentRow, currentCol, discsInThisDirection);
                        }
                    }
                }

                currentRow += direction[0];
                currentCol += direction[1];

                if (currentRow < 0 || currentRow >= getBoardSize() || currentCol < 0 || currentCol >= getBoardSize()) {
                    discsInThisDirection.clear();
                    break;
                }
            }
        }
        return visitedPositions.size();
    }

    /**
     * Gets the first player.
     *
     * @return the first player.
     */
    @Override
    public Player getFirstPlayer() {
        return this.player1;
    }

    /**
     * Gets the second player.
     *
     * @return the second player.
     */
    @Override
    public Player getSecondPlayer() {
        return this.player2;
    }

    /**
     * Sets the players.
     *
     * @param player1 the first player.
     * @param player2 the second player.
     */
    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Checks if it is the first player's turn.
     *
     * @return true if it is the first player's turn, false otherwise.
     */
    @Override
    public boolean isFirstPlayerTurn() {
        return this.turnOfFirstPlayer;
    }

    /**
     * Checks if the game is finished.
     *
     * @return true if the game is finished, false otherwise.
     */
    @Override
    public boolean isGameFinished() {
        if (!ValidMoves().isEmpty()) // If there are still valid moves, the game is not finished
            return false;

        Disc currentDisc;
        int discsOfFirstPlayer = 0, discsOfSecondPlayer = 0;
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                currentDisc = this.board[row][col];
                if (currentDisc != null) {
                    if (currentDisc.getOwner().equals(getFirstPlayer())) discsOfFirstPlayer++;
                    else if (currentDisc.getOwner().equals(getSecondPlayer())) discsOfSecondPlayer++;
                }
            }
        }

        int winnerPlayerNum = 1, loserPlayerNum = 2;
        int discsOfWinner = discsOfFirstPlayer, discsOfLoser = discsOfSecondPlayer;

        if (discsOfFirstPlayer < discsOfSecondPlayer) {
            winnerPlayerNum = 2;
            discsOfWinner = discsOfSecondPlayer;
            loserPlayerNum = 1;
            discsOfLoser = discsOfFirstPlayer;
        } else if (discsOfFirstPlayer == discsOfSecondPlayer) {
            System.out.println("It's a draw!");
            return true;
        }

        if (winnerPlayerNum == 1)
            getFirstPlayer().addWin();
        else getSecondPlayer().addWin();

        System.out.println("Player " + winnerPlayerNum + " wins with " + discsOfWinner + " discs! Player " + loserPlayerNum + " had " + discsOfLoser + " discs.");
        return true;
    }

    /**
     * Resets the game.
     */
    @Override
    public void reset() {
        // Clear the board
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                this.board[row][col] = null;
            }
        }

        moveHistory.clear(); // Clear the move history stack

        // Initialize the four first discs
        this.board[3][3] = new SimpleDisc(this.player1);
        this.board[4][4] = new SimpleDisc(this.player1);
        this.board[3][4] = new SimpleDisc(this.player2);
        this.board[4][3] = new SimpleDisc(this.player2);

        if (currentPlayer != null && opponentPlayer != null) {
            this.currentPlayer.reset_bombs_and_unflippedable();
            this.opponentPlayer.reset_bombs_and_unflippedable();
        }
        this.turnOfFirstPlayer = true; // The first player to play is player1
        updatePlayers(); // Update the current and opponent player
    }

    /**
     * Undoes the last move.
     */
    @Override
    public void undoLastMove() {
        if (getFirstPlayer().isHuman() && getSecondPlayer().isHuman()) {
            if (moveHistory.isEmpty()) {
                System.out.println("\tNo previous move available to undo.\n");
                return;
            }
            Move lastMove = moveHistory.pop(); // Get the last move
            Position position = lastMove.position();
            Disc disc = lastMove.disc();
            List<String> flippedPositions = lastMove.flippedPositions();
            System.out.println("Undoing the last move:");
            System.out.println("\tUndo: removing " + disc.getType() + " from (" + position.row() + ", " + position.col() + ")");

            // Remove the disc from the board
            this.board[position.row()][position.col()] = null;

            // If the disc was a special disc, increment the count
            if (disc.getType().equals("⭕")) opponentPlayer.number_of_unflippedable++;
            else if (disc.getType().equals("💣")) opponentPlayer.number_of_bombs++;

            // Revert the flipped discs
            for (String pos : flippedPositions) {
                String[] posArr = pos.split(",");
                int row = Integer.parseInt(posArr[0]);
                int col = Integer.parseInt(posArr[1]);
                this.board[row][col].setOwner(this.currentPlayer);
                System.out.println("\tUndo: flipping back " + this.board[row][col].getType() + " in (" + row + ", " + col + ")");
            }
            System.out.println();

            // Switch the turns back
            this.turnOfFirstPlayer = !this.turnOfFirstPlayer;
            updatePlayers();
        }
    }

    /**
     * Checks if the move is valid.
     *
     * @param position the position to check.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(Position position) {
        return this.board[position.row()][position.col()] == null && countFlips(position) > 0;
    }

    /**
     * Checks if the special disc can be placed.
     *
     * @param disc the disc to check.
     * @return true if the special disc can be placed, false otherwise.
     */
    private boolean freeSpecialDisc(Disc disc) {
        if (!disc.getType().equals("⬤")) {
            if (disc.getType().equals("⭕") && currentPlayer.number_of_unflippedable <= 0) return false;
            else if (disc.getType().equals("💣") && currentPlayer.number_of_bombs <= 0) return false;
        }
        return true;
    }

    /**
     * Updates the current and opponent players.
     */
    private void updatePlayers() {
        if (isFirstPlayerTurn()) {
            this.currentPlayer = getFirstPlayer();
            this.opponentPlayer = getSecondPlayer();

        } else {
            this.currentPlayer = getSecondPlayer();
            this.opponentPlayer = getFirstPlayer();
        }
    }

    /**
     * Gets the current player's number.
     *
     * @return the current player's number.
     */
    private int getCurrentPlayerNum() {
        if (this.currentPlayer == getFirstPlayer())
            return 1;
        return 2;
    }

    /**
     * Adds the relevant bomb discs to a given set.
     *
     * @param directions           the directions to check.
     * @param currentRow           the current row.
     * @param currentCol           the current column.
     * @param discsInThisDirection the discs in this direction.
     */
    private void appendBombDiscs(int[][] directions, int currentRow, int currentCol, Set<String> discsInThisDirection) {
        for (int[] tempDirection : directions) {
            int tempRow = currentRow + tempDirection[0];
            int tempCol = currentCol + tempDirection[1];

            if (tempRow >= 0 && tempRow < getBoardSize() && tempCol >= 0 && tempCol < getBoardSize()) {
                Disc tempDisc = this.board[tempRow][tempCol];
                String positionKey = tempRow + "," + tempCol;

                // Skip if we've already visited this position
                if (discsInThisDirection.contains(positionKey)) {
                    continue;
                }

                if (tempDisc != null && tempDisc.getOwner() != null) {
                    if (tempDisc.getOwner().equals(opponentPlayer) && !tempDisc.getType().equals("⭕")) {
                        discsInThisDirection.add(positionKey); // Mark as visited
                        if (tempDisc.getType().equals("💣")) {
                            appendBombDiscs(directions, tempRow, tempCol, discsInThisDirection);
                        }
                    }
                }
            }
        }
    }
}