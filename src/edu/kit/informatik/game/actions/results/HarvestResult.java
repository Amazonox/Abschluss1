package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.elements.Vegetables;

public class HarvestResult extends ActionResult {
    private int amount;
    private Vegetables vegetable;

    public HarvestResult(final int amount, final Vegetables vegetable) {
        this.amount = amount;
        this.vegetable = vegetable;
    }

    @Override
    public String toString() {
        return "You have harvested %d %s.".formatted(amount, vegetable.getName().fromAmount(amount))
                + super.toString();
    }
}
