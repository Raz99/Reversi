import java.util.ArrayList;

/**
 * Represents a move in the game.
 */
public class Move {
    private Position position;
    private Disc disc;
    private ArrayList<String> flippedPositions;

    /**
     * Constructs a Move with the specified position, disc, and flipped positions.
     * @param position the position of the move.
     * @param disc the disc placed in the move.
     * @param flippedPositions the positions of the flipped discs.
     */
    public Move(Position position, Disc disc, ArrayList<String> flippedPositions) {
        this.position = position;
        this.disc = disc;
        this.flippedPositions = flippedPositions;
    }

    /**
     * Gets the position of the move.
     * @return the position of the move.
     */
    public Position position() {
        return position;
    }

    /**
     * Gets the disc placed in the move.
     * @return the disc placed in the move.
     */
    public Disc disc() {
        return disc;
    }

    /**
     * Gets the positions of the flipped discs.
     * @return the positions of the flipped discs.
     */
    public ArrayList<String> flippedPositions() {
        return flippedPositions;
    }
}