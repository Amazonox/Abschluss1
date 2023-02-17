package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.BuyLandResult;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Tile;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.utils.Vector2d;

public class BuyLand implements Action {
    TileScrambler tileScrambler;
    Vector2d newPosition;

    public BuyLand(final Vector2d newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    public ActionResult execute(Player player, Market market, TileScrambler tiles) throws InvalidArgumentException {
        final int manhattanDistance = player.getLand().getManhattanDistanceForNewTile(this.newPosition);
        final int price = 10 * (manhattanDistance - 1);
        if (this.tileScrambler.isEmpty()) throw new InvalidArgumentException(ErrorMessage.NO_MORE_TILES_IN_GAME);
        player.spendGold(price);
        final Tile tile = this.tileScrambler.getNext().getTile();
        player.getLand().setTile(tile, this.newPosition);
        return new BuyLandResult(tile.getTiles(), price);
    }
}
