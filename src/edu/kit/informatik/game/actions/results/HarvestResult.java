package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.elements.Vegetables;

/**
 * this is an action result containing information about the amount and the kind of vegetable that was effected
 * by a harvest action
 *
 * @author uzovo
 * @version 1.0
 */
public class HarvestResult extends ActionResult {
    private final int amount;
    private final Vegetables vegetable;

    /**
     * this instatiates a new harvest result containing the given amount and vegetable
     * @param amount the amount of vegetables that were harvested
     * @param vegetable the kind of vegetable that was harvested
     */
    public HarvestResult(final int amount, final Vegetables vegetable) {
        this.amount = amount;
        this.vegetable = vegetable;
    }

    @Override
    public String toString() {
        return "You have harvested %d %s.".formatted(this.amount, this.vegetable.getName().fromAmount(this.amount))
                + super.toString();
    }
}
