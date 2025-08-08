import java.util.Comparator;
import java.util.List;

/**
 * Represents a greedy AI player in the game.
 */
public class GreedyAI extends AIPlayer{
    /**
     * Constructs a GreedyAI with the specified player type.
     * @param isPlayerOne true if the player is the first player, false otherwise.
     */
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Makes a move based on the current game status.
     * @param gameStatus the current game status.
     * @return the move made by the AI.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();

        // Comparator by row
        Comparator<Position> rowComparator = new Comparator<Position>(){
            @Override
            public int compare(Position p1, Position p2){
                return p2.row() - p1.row();
            }
        };

        // Comparator by column
        Comparator<Position> colComparator = new Comparator<Position>(){
            @Override
            public int compare(Position p1, Position p2){
                return p2.col() - p1.col();
            }
        };

        validMoves.sort(rowComparator);
        validMoves.sort(colComparator);

        Position selectedPosition = validMoves.getFirst();
        int maxFlips = gameStatus.countFlips(selectedPosition);

        for(int i = 1; i < validMoves.size(); i++){
            Position currentPosition = validMoves.get(i);
            int currentFlips = gameStatus.countFlips(currentPosition);
            if(currentFlips > maxFlips){
                selectedPosition = currentPosition;
                maxFlips = currentFlips;
            }
        }
        return new Move(selectedPosition, new SimpleDisc(this), null);
    }
}