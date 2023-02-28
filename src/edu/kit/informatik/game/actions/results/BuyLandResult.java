package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.elements.TileType;
import edu.kit.informatik.ui.Main;

/**
 * an action result containing infos about the cost and the purchased tile of a land buy action.
 *
 * @author uzovo
 * @version 1.0
 */
public class BuyLandResult extends ActionResult {
    private final TileType tile;
    private final int gold;

    /**
     * this instantiates a new buy land result with the given tile and gold information
     *
     * @param tile the tile that was bought
     * @param gold the gold that was spent when buying the tile
     */
    public BuyLandResult(final TileType tile, final int gold) {
        this.tile = tile;
        this.gold = gold;
    }

    @Override
    public String toString() {
        return Main.BUY_RESULT_SCEMEATIC
                .formatted(this.tile.getName(), this.gold) + super.toString();
    }
}
