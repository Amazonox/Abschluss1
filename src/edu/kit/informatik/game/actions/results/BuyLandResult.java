package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.ui.Main;

public class BuyLandResult extends ActionResult {
    private final Tiles tile;
    private final int gold;

    public BuyLandResult(final Tiles tile, final int gold) {
        this.tile = tile;
        this.gold = gold;
    }

    @Override
    public String toString() {
        return Main.BUY_RESULT_SCEMEATIC
                .formatted(this.tile.getName(),this.gold) + super.toString();
    }
}
