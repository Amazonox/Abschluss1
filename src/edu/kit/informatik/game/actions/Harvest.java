package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.utils.Vector2d;

public class Harvest extends Action{
    private Vector2d location;
    private int amount;

    public Harvest(final Player player, final Market market, final Vector2d location, final int amount,
                   final TileScrambler tiles) {
        super(player, market,tiles);
        this.location = location;
        this.amount = amount;
    }

    @Override
    public void execute() throws InvalidArgumentException {
        final CultivatableTile cultivatableTile = this.player.getLand().getTile(this.location);
        final Vegetables vegetables = cultivatableTile.removeVegetables(this.amount);
        this.player.getLand().getBarn().storeVegetables(vegetables, this.amount);
    }
}
