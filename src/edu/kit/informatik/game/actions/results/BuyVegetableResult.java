package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.ui.Main;

public class BuyVegetableResult extends ActionResult {
    private final Vegetables vegetable;
    private final int price;

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
