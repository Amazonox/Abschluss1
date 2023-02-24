package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.ui.Main;

/**
 * this action result stores information about the vegetable that was bought, as well as the price of that vegetable
 * for when a buy vegetable action is executed
 *
 * @author uzovo
 * @version 1.0
 */
public class BuyVegetableResult extends ActionResult {
    private final Vegetables vegetable;
    private final int price;

    /**
     * this instantiates a new buy vegetable result containing the given vegetable as well as its price
     * @param vegetable the vegetable that was bought
     * @param price the price the vegetable was bought for
     */
    public BuyVegetableResult(final Vegetables vegetable, final int price) {
        this.vegetable = vegetable;
        this.price = price;
    }

    @Override
    public String toString() {
        return Main.BUY_RESULT_SCEMEATIC
                        .formatted(this.vegetable.getSingular(), this.price) + super.toString();
    }
}
