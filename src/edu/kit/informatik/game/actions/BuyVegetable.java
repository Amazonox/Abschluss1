package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.BuyVegetableResult;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.GameException;

public class BuyVegetable implements Action {
    Vegetables vegetable;

    public BuyVegetable(final Vegetables vegetable) {
        this.vegetable = vegetable;
    }


    @Override
    public ActionResult execute(Player player, Market market, TileScrambler tiles) throws GameException {
        int price = market.getSellingPrice(this.vegetable);
        player.spendGold(price);
        player.getLand().getBarn().storeVegetables(this.vegetable);
        return new BuyVegetableResult(this.vegetable,price);
    }
}
