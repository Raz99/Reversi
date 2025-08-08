import java.util.List;
import java.util.Random;

/**
 * Represents a random AI player in the game.
 */
public class RandomAI extends AIPlayer{
    /**
     * Constructs a RandomAI with the specified player type.
     * @param isPlayerOne true if the player is the first player, false otherwise.
     */
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Makes a move based on the current game status.
     * @param gameStatus the current game status.
     * @return the move made by the AI.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random random = new Random();

        List<Position> validMoves = gameStatus.ValidMoves();
        Position selectedPosition = validMoves.get(random.nextInt(validMoves.size())); // random position from valid moves

        int randDisc = random.nextInt(1, 4); // random number between 1 and 3
        Disc selectedDisc = pickDisc(randDisc, random);
        return new Move(selectedPosition, selectedDisc, null);
    }

    /**
     * Picks a disc based on the random number.
     * @param randDisc the random number.
     * @param random the random instance.
     * @return the selected disc.
     */
    private Disc pickDisc(int randDisc, Random random) {
        Disc returnDisc = null;
        switch (randDisc) {
            case 1: // SimpleDisc
                returnDisc = new SimpleDisc(this);
                break;
            case 2: // UnflippableDisc
                if (this.number_of_unflippedable > 0)
                    returnDisc = new UnflippableDisc(this);
                else {
                    randDisc = random.nextInt(1, 3); // random number between 1 and 2
                    if (randDisc == 2) randDisc = 3;
                    return pickDisc(randDisc, random);
                }
                break;
            case 3: // BombDisc
                if (this.number_of_bombs > 0)
                    returnDisc = new BombDisc(this);
                else {
                    randDisc = random.nextInt(1, 3); // random number between 1 and 2
                    return pickDisc(randDisc, random);
                }
                break;
        }
        return returnDisc;
    }
}