package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.SellResult;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.ui.GameException;

public class Sell implements Action {
    private VegetableAmounts vegetableAmountsToSell;

    public Sell(final VegetableAmounts vegetableAmountsToSell) {
        this.vegetableAmountsToSell = vegetableAmountsToSell;
    }

    @Override
    public ActionResult execute(Player player, Market market, TileScrambler tiles) throws GameException {
        final Barn barn = player.getLand().getBarn();
        barn.removeVegetables(this.vegetableAmountsToSell);
        final int price = market.sellVegetables(this.vegetableAmountsToSell);
        player.addGold(price);
        return new SellResult(this.vegetableAmountsToSell.getTotalNumberOfVegetables(),price);
    }
}
