package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.ui.Main;

/**
 * this action result contains information about the amount of sold vegetables and the gold that was earned by
 * a sell action
 *
 * @author uzovo
 * @version 1.0
 */
public class SellResult extends ActionResult {
    private final int amount;
    private final int gold;

    /**
     * this instantiates a new sell result containing the given amount and gold
     * @param amount the amount of vegetables that were sold
     * @param gold the gold eared by selling the vegetables
     */

    public SellResult(final int amount, final int gold) {
        this.amount = amount;
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "You have sold %d %s for %d gold."
                .formatted(this.amount, Main.VEGETABEL.fromAmount(this.amount), this.gold)
                + super.toString();
    }
}
