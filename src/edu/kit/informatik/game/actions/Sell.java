package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.ui.InvalidArgumentException;

public class Sell extends Action{
    VegetableAmounts vegetableAmountsToSell;

    public Sell(final Player player, final Market market,
                final VegetableAmounts vegetableAmountsToSell,
                final TileScrambler tiles) {
        super(player, market,tiles);
        this.vegetableAmountsToSell = vegetableAmountsToSell;
    }

    @Override
    public void execute() throws InvalidArgumentException {
        Barn barn = this.player.getLand().getBarn();
        barn.removeVegetables(vegetableAmountsToSell);
        market.sellVegetables(vegetableAmountsToSell);
    }
}
