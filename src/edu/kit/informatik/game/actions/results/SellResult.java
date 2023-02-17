package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.ui.Main;

public class SellResult extends ActionResult {
    private final int amount;
    private final int gold;

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
