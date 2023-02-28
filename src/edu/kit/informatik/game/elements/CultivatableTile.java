package edu.kit.informatik.game.elements;

import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Counter;

/**
 * This is a core element. It lets the player plant and harvest vegetables as well as doubling the amount vegetables
 * after waiting their specified amount of time the vegetable needs to grow util the capacity of the tile type is reached.
 * Each tile can only hold the vegetables specified by its tile type.
 *
 * @author uzovo
 * @version 1.0
 */
public class CultivatableTile implements Tile {
    private Vegetables currentlyPlantedVegetables;
    private Counter counter;
    private final TileType tileType;
    private int numberOfVegetables;
    private int numberOfVegetablesGrown;

    /**
     * This instantiates a new cultivatable tile with no planted vegetable, no counter and the specified tile type
     * @param tileType The tile type, this cultivatable tile should belong to
     */
    public CultivatableTile(final TileType tileType) {
        this.currentlyPlantedVegetables = null;
        this.counter = null;
        this.numberOfVegetables = 0;
        this.tileType = tileType;
        this.numberOfVegetablesGrown = 0;
    }

    /**
     * This instantiates a copy of the given Tile with separate but equivalent specifications. The vegetables on this
     * clone, unless altered (replanted) do not grow
     *
     * @param cultivatableTile The cultivatable tile that should be copied
     */
    public CultivatableTile(final CultivatableTile cultivatableTile) {
        this.counter = cultivatableTile.counter == null ? null : new Counter(cultivatableTile.counter);
        this.numberOfVegetables = cultivatableTile.numberOfVegetables;
        this.tileType = cultivatableTile.tileType;
        this.currentlyPlantedVegetables = cultivatableTile.currentlyPlantedVegetables;
        this.numberOfVegetablesGrown = cultivatableTile.numberOfVegetablesGrown;
    }

    /**
     * This plants a new vegetable on the tile if there is no vegetable currently planted on this tile
     * and the vegetable is cultivatable on the tile type
     * @param vegetable the vegetable which should be planted on the tile
     * @throws GameException If there are already vegetables on this tile or if the vegetable is not cultivatable on the
     * tile type
     */
    public void plantVegetable(final Vegetables vegetable) throws GameException {
        if (this.currentlyPlantedVegetables != null) throw new GameException(
                ErrorMessage.TILE_ALREADY_HAS_VEGETABLE
        );
        if (!this.tileType.getCultivatableVegetables().contains(vegetable))
            throw new GameException(ErrorMessage.VEGETABLE_NOT_CULTIVATABLE_ON_THIS_TILE);
        this.currentlyPlantedVegetables = vegetable;
        this.numberOfVegetables++;
        this.counter = new Counter(this.currentlyPlantedVegetables.getRoundsToGrow(), this::growVegetables);
    }

    /**
     * This removes a specified amount of vegetables from this tile, if there are enough. If there are no more vegetables
     * on this tile, the currently planted vegetable and the timer are removed.
     * @param amount The amount of vegetable which should be removed from this tile
     * @return The vegetable, that was planted on this tile.
     * @throws GameException If there are no or not enough vegetables on this tile
     */
    public Vegetables removeVegetables(final int amount) throws GameException {
        if (this.numberOfVegetables == 0) throw new GameException(ErrorMessage.NO_VEGETABLE_ON_TILE);
        if (this.numberOfVegetables < amount)
            throw new GameException(ErrorMessage.NOT_ENOUGH_VEGETABLES_ON_TILE);
        this.numberOfVegetables -= amount;
        final Vegetables plantedVegetable = this.currentlyPlantedVegetables;
        if (this.numberOfVegetables == 0) {
            this.currentlyPlantedVegetables = null;
            this.counter.removeCounter();
            this.counter = null;
        } else {
            if (this.counter.isFinished()) {
                this.counter.restartCounter();
            }
        }
        return plantedVegetable;
    }

    private boolean growVegetables(){
        this.numberOfVegetablesGrown
            = Math.min(this.tileType.getCapacity(), this.numberOfVegetables * 2) - this.numberOfVegetables;
        this.numberOfVegetables = Math.min(this.tileType.getCapacity(), this.numberOfVegetables * 2);
        return this.numberOfVegetables != this.tileType.getCapacity();
    }

    /**
     * This returns the amount of vegetables that have grown since the last call to this method
     * @return the amounts of vegetables, that have grown since the last call to this method
     */
    public int getNumberOfVegetablesGrown() {
        final int vegetablesGrown = this.numberOfVegetablesGrown;
        this.numberOfVegetablesGrown = 0;
        return vegetablesGrown;
    }

    @Override
    public TileType getTileType() {
        return this.tileType;
    }

    @Override
    public Tile clone() {
        return new CultivatableTile(this);
    }

    @Override
    public String toString() {
        final String counter = this.counter == null || this.counter.isFinished()
                ? "*" : Integer.toString(this.counter.getRoundsToEnd());
        final String format = switch (this.tileType.getAbbreviation().length()) {
            case 1 -> " %s %s ";
            case 2 -> " %s %s";
            case 3 -> "%s %s";
            default -> throw new IllegalStateException("Unexpected pricePoint: " + this.tileType.getAbbreviation().length());
        };
        final StringBuilder result = new StringBuilder();
        result.append(format.formatted(this.tileType.getAbbreviation(), counter)).append(System.lineSeparator());
        final String vegetable = this.currentlyPlantedVegetables == null ? Main.SPACE : this.currentlyPlantedVegetables.getAbbreviation();
        result.append(String.format("  %s  ", vegetable)).append(System.lineSeparator());
        result.append(String.format(" %s/%s ", this.numberOfVegetables, this.tileType.getCapacity()));
        return result.toString();
    }
}
