package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.BuyVegetableResult;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.GameException;

/**
 * This action lets the player buy a new vegetable. For this, the amount of money the vegetable costs on the market,
 * is extracted from the player and the vegetable is put in the players barn
 *
 * @author uzovo
 * @version 1.0
 */

public class BuyVegetable implements Action {
    private final Vegetables vegetable;

    /**
     * this initialises a new buy vegetable action for the given vegetable
     *
     * @param vegetable the vegetable, that should be bought
     */
    public BuyVegetable(final Vegetables vegetable) {
        this.vegetable = vegetable;
    }


    @Override
    public ActionResult execute(final Player player, final Market market, final TileScrambler tiles) throws GameException {
        final int price = market.getSellingPrice(this.vegetable);
        player.spendGold(price);
        player.getLand().getBarn().storeVegetables(this.vegetable);
        return new BuyVegetableResult(this.vegetable, price);
    }
}
