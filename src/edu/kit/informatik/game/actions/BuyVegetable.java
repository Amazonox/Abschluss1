package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.InvalidArgumentException;

import java.util.List;

public class BuyVegetable extends Action{
    Vegetables vegetable;

    public BuyVegetable(final Player player, final Market market,
                        final Vegetables vegetable, final TileScrambler tiles) {
        super(player, market,tiles);
        this.vegetable = vegetable;
    }

    @Override
    public void execute() throws InvalidArgumentException {
        this.player.spendGold(this.market.getSellingPrice(this.vegetable));
        this.player.getLand().getBarn().storeVegetables(this.vegetable);
    }
}
