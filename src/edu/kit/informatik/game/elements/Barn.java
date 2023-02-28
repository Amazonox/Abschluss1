package edu.kit.informatik.game.elements;

import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Counter;

import java.util.Collection;

/**
 * This is a core element of the game. The barn holds the players Vegetables and makes them spoil: After 6 rounds where
 * the barn is filled, all vegetables are removed.
 *
 * @author uzovo
 * @version 1.0
 */
public class Barn implements Tile {
    private VegetableAmounts storedVegetables;
    private Counter counter;
    private final TileType tileType;
    private boolean hasFoodSpoiled;

    /**
     * This initialises a new barn with the barn tile type, no stored vegetables and no counter.
     */
    public Barn() {

        this.tileType = TileType.BARN;
        this.storedVegetables = new VegetableAmounts();
        this.hasFoodSpoiled = false;
        this.counter = null;
    }

    /**
     * This is a copy constructor for a barn. The copied barn does not have an updating Counter, so its vegetables
     * won't spoil unless updated
     *
     * @param barn The barn that should be copied
     */
    public Barn(final Barn barn) {
        this.tileType = barn.tileType;
        this.storedVegetables = new VegetableAmounts(barn.storedVegetables);
        this.hasFoodSpoiled = barn.hasFoodSpoiled;
        this.counter = barn.counter == null ? null : new Counter(barn.counter);
    }

    /**
     * This stores the given vegetables. Duplicates are treated as multiple vegetables that should be stored.
     * This starts a new mold timer if there was none
     * @param vegetables The vegetables which should be stored
     */
    public void storeVegetables(final Collection<Vegetables> vegetables) {
        this.storedVegetables.addAll(vegetables);
        this.startMoldTimer();
    }

    /**
     * This stores a given amount of one vegetable.
     * This starts a new mold timer if there was none
     * @param vegetable the vegetable that should be stored
     * @param amount the amount of that vegetable which should be stored
     * @throws GameException If the amount is bellow zero
     */
    public void storeVegetables(final Vegetables vegetable, final int amount) throws GameException {
        if (amount < 0) throw new GameException(ErrorMessage.BELOW_ZERO_INTEGER);
        this.storedVegetables.changeVegetableAmountBy(vegetable, amount);
        this.startMoldTimer();
    }

    /**
     * This stores one of the given vegetable.
     * This starts a new mold timer if there was none
     * @param vegetable the vegetable which should be stored
     * @throws GameException If the amount magically switches below zero
     */
    public void storeVegetables(final Vegetables vegetable) throws GameException {
        this.storeVegetables(vegetable, 1);

    }

    private void startMoldTimer() {
        if (!this.storedVegetables.isEmpty() && (this.counter == null || this.counter.isFinished())) {
            this.counter = new Counter(6, () -> {
                this.storedVegetables.resetAmounts();
                this.hasFoodSpoiled = true;
                return false;
            });
        }
    }

    private void stopMoldTimer(){
        if (this.storedVegetables.isEmpty() && this.counter != null) {
            this.counter.removeCounter();
            this.counter = null;
        }
    }

    /**
     * Removes one of the vegetable from the barn, if there are enough. If the barn is empty, the mold timer is
     * removed
     * @param vegetable The vegetable which should be removed
     * @throws GameException If there are no more of this vegetable in the barn
     */
    public void removeVegetable(final Vegetables vegetable) throws GameException {
        if (!this.storedVegetables.changeVegetableAmountBy(vegetable, -1))
            throw new GameException(ErrorMessage.NOT_ENOUGH_VEGETABLES);
        this.stopMoldTimer();
    }

    /**
     * This removes the given amount of vegetables from the barn, if there are enough vegetables in the barn.
     * If the barn is empty, the mold timer is removed
     * @param vegetableAmounts Positive amounts of vegetables, which should be removed
     * @throws GameException If there are not enough vegetables in the barn
     */
    public void removeVegetables(final VegetableAmounts vegetableAmounts) throws GameException {
        final VegetableAmounts vegetableAmountsAfter = new VegetableAmounts(this.storedVegetables);

        for (final Vegetables vegetables : Vegetables.values()) {
            if (!vegetableAmountsAfter.changeVegetableAmountBy(vegetables,
                    -vegetableAmounts.getAmount(vegetables)))
                throw new GameException(ErrorMessage.NOT_ENOUGH_VEGETABLES);
        }
        this.storedVegetables = vegetableAmountsAfter;
        this.stopMoldTimer();
    }

    /**
     * This method checks if there is at least one of the given vegetable in the barn
     * @param vegetable the vegetable that is checked
     * @return True, if there is at least one of the specified vegetable in the barn, otherwise false
     */
    public boolean containsVegetable(final Vegetables vegetable){
        return this.storedVegetables.getAmount(vegetable) != 0;
    }

    /**
     * This returns if the vegetables have spoiled after last round. After calling this method once,
     * it returns false until the vegetables spoil one more time
     * @return True if the vegetables have spoiled since the last call to this method, otherwise false.
     */
    public boolean hasFoodSpoiled() {
        final boolean foodSpoiled = this.hasFoodSpoiled;
        this.hasFoodSpoiled = false;
        return foodSpoiled;
    }

    @Override
    public TileType getTileType() {
        return this.tileType;
    }

    @Override
    public Tile clone() {
        return new Barn(this);
    }

    /**
     * Returns the number of rounds, the food spoils in
     * @return The number of rounds, the food spoils in
     */
    public int foodSpoilsIn() {
        return this.counter.getRoundsToEnd();
    }

    /**
     * This returns the currently stored vegetables
     * @return The vegetables and their amounts, that are currently stored in this barn
     */
    public VegetableAmounts getStoredVegetables() {
        return new VegetableAmounts(this.storedVegetables);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(Main.SPACE.repeat(Tile.TO_STRING_SIZE.x())).append(System.lineSeparator());
        final String counter = this.counter == null || this.counter.isFinished()
                ? Main.NO_COUNTER_INDICATOR : Integer.toString(this.counter.getRoundsToEnd());
        result.append(String.format(" %s %s ", this.tileType.getAbbreviation(), counter)).append(System.lineSeparator());
        result.append(Main.SPACE.repeat(Tile.TO_STRING_SIZE.x()));
        return result.toString();
    }
}
