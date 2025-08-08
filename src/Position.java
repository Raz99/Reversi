/**
 * Represents a position on the board.
 */
public class Position {
    private int row;
    private int col;

    /**
     * Constructs a Position with the specified row and column.
     * @param row the row of the position.
     * @param col the column of the position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row of the position.
     * @return the row of the position.
     */
    public int row() {
        return this.row;
    }

    /**
     * Gets the column of the position.
     * @return the column of the position.
     */
    public int col() {
        return this.col;
    }
}