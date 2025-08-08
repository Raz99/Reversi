/**
 * Represents a bomb disc in the game.
 */
public class BombDisc implements Disc {
    private Player owner;

    /**
     * Constructs a BombDisc with the specified owner.
     * @param player the owner of the disc.
     */
    public BombDisc(Player player) {
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
     * @return "💣" for Bomb Disc.
     */
    @Override
    public String getType() {
        return "💣";
    }
}