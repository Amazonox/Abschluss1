package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.HarvestResult;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.utils.Vector2d;

public class Harvest implements Action {
    private final Vector2d location;
    private final int amount;

    public Harvest(final Vector2d location, final int amount) {
        this.location = location;
        this.amount = amount;
    }


    @Override
    public ActionResult execute(Player player, Market market, TileScrambler tiles) throws GameException {
        final CultivatableTile cultivatableTile = player.getLand().getTile(this.location);
        final Vegetables vegetables = cultivatableTile.removeVegetables(this.amount);
        player.getLand().getBarn().storeVegetables(vegetables, this.amount);
        return new HarvestResult(this.amount, vegetables);
    }
}
