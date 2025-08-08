/**
 * Represents an unflippable disc in the game.
 */
public class UnflippableDisc implements Disc {
    private Player owner;

    /**
     * Constructs an UnflippableDisc with the specified owner.
     * @param player the owner of the disc.
     */
    public UnflippableDisc(Player player) {
        this.owner = player;
    }

    /**
     * Get the player who owns the Disc.
     *
     * @return The player who is the owner of this game disc.
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Set the player who owns the Disc.
     *
     */
    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Get the type of the disc.
     *
     * @return "⭕" for Unflippable Disc.
     */
    @Override
    public String getType() {
        return "⭕";
    }
}