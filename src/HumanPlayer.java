/**
 * Represents a human player in the game.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a HumanPlayer with the specified player type.
     * @param isPlayerOne true if the player is the first player, false otherwise.
     */
    public HumanPlayer(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Checks if the player is human.
     * @return true if the player is human, false otherwise.
     */
    @Override
    boolean isHuman() {
        return true;
    }
}