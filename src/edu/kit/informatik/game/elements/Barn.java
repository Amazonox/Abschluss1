package edu.kit.informatik.game.elements;

import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Counter;

import java.util.Collection;

public class Barn implements Tile {
    private VegetableAmounts storedVegetables;
    private Counter counter;
    private final Tiles tiles;

    private boolean hasFoodSpoiled;

    public Barn(final Tiles tiles) {

        this.tiles = tiles;
        this.storedVegetables = new VegetableAmounts();
        this.hasFoodSpoiled = false;
    }

    /**
     * clones a barn so that it does not update but has the same values
     *
     * @param barn
     */
    public Barn(final Barn barn) {
        this.tiles = barn.tiles;
        this.storedVegetables = new VegetableAmounts(barn.storedVegetables);
        this.hasFoodSpoiled = barn.hasFoodSpoiled;
        this.counter = barn.counter == null ? null : new Counter(barn.counter);
    }

    public void storeVegetables(final Collection<Vegetables> vegetables) {
        this.storedVegetables.addAll(vegetables);
        this.startMoldTimer();
    }

    public void storeVegetables(final Vegetables vegetable, final int amount) throws GameException {
        if (amount < 0) throw new GameException(ErrorMessage.BELOW_ZERO_INTEGER);
        this.storedVegetables.changeVegetableAmountBy(vegetable, amount);
        this.startMoldTimer();
    }

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

    public void removeVegetable(final Vegetables vegetable) throws GameException {
        if (!this.storedVegetables.changeVegetableAmountBy(vegetable, -1))
            throw new GameException(ErrorMessage.NOT_ENOUGH_VEGETABLES);
        if (this.storedVegetables.isEmpty()) {
            this.counter.removeCounter();
            this.counter = null;
        }
    }

    /**
     * the vegetables to remove
     *
     * @param vegetableAmounts positive amounts
     * @throws GameException
     */
    public void removeVegetables(final VegetableAmounts vegetableAmounts) throws GameException {
        final VegetableAmounts vegetableAmountsAfter = new VegetableAmounts(this.storedVegetables);

        for (final Vegetables vegetables : Vegetables.values()) {
            if (!vegetableAmountsAfter.changeVegetableAmountBy(vegetables,
                    -vegetableAmounts.getAmount(vegetables)))
                throw new GameException(ErrorMessage.NOT_ENOUGH_VEGETABLES);
        }
        this.storedVegetables = vegetableAmountsAfter;
    }

    public boolean hasFoodSpoiled() {
        final boolean foodSpoiled = this.hasFoodSpoiled;
        this.hasFoodSpoiled = false;
        return foodSpoiled;
    }

    @Override
    public Tiles getTiles() {
        return this.tiles;
    }

    @Override
    public Tile clone() {
        return new Barn(this);
    }

    public int foodSpoilsIn() {
        return this.counter.getRoundsToEnd();
    }

    public VegetableAmounts getStoredVegetables() {
        return new VegetableAmounts(this.storedVegetables);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(Main.SPACE.repeat(Tile.TO_STRING_SIZE.x())).append(System.lineSeparator());
        final String counter = this.counter == null || this.counter.isFinished()
                ? "*" : Integer.toString(this.counter.getRoundsToEnd());
        result.append(String.format(" %s %s ", this.tiles.getAbbreviation(), counter)).append(System.lineSeparator());
        result.append(Main.SPACE.repeat(Tile.TO_STRING_SIZE.x()));
        return result.toString();
    }
}
