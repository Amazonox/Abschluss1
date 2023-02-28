package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.SellResult;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.ui.GameException;

/**
 * This action lets the player sell a specific amount of vegetables which are to be removed from the barn,
 * sold on the market and the money is given to the player
 *
 * @author uzovo
 * @version 1.0
 */
public class Sell implements Action {
    private final VegetableAmounts vegetableAmountsToSell;

    /**
     * This instantiates a new sell action for the given amount of vegetables
     * @param vegetableAmountsToSell the vegetables which should be sold
     */

    public Sell(final VegetableAmounts vegetableAmountsToSell) {
        this.vegetableAmountsToSell = vegetableAmountsToSell;
    }

    @Override
    public ActionResult execute(final Player player, final Market market, final TileScrambler tiles) throws GameException {
        final Barn barn = player.getLand().getBarn();
        barn.removeVegetables(this.vegetableAmountsToSell);
        final int price = market.sellVegetables(this.vegetableAmountsToSell);
        player.addGold(price);
        return new SellResult(this.vegetableAmountsToSell.getTotalNumberOfVegetables(),price);
    }
}
