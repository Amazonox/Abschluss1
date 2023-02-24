package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.BuyLandResult;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Tile;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.utils.Vector2d;

/**
 * this action lets the player use his money to new land. The price is calculated with the manhandle distance from the
 * new tile, to the barn of the player and the new tile is chosen by the tile scrambler. Tiles can only be placed
 * above, left or right of existing tiles.
 *
 * @author uzovo
 * @version 1.0
 */
public class BuyLand implements Action {
    private final Vector2d newPosition;

    /**
     * this instantiates a new buy land action given the position, where the new tile should be located
     *
     * @param newPosition the position the bought tile should be placed at
     */
    public BuyLand(final Vector2d newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    public ActionResult execute(final Player player, final Market market, final TileScrambler tileScrambler)
            throws GameException {
        final int manhattanDistance = player.getLand().getManhattanDistanceForNewTile(this.newPosition);
        final int price = 10 * (manhattanDistance - 1);
        if (tileScrambler.isEmpty()) throw new GameException(ErrorMessage.NO_MORE_TILES_IN_GAME);
        player.spendGold(price);
        final Tile tile = tileScrambler.getNext().getTile();
        player.getLand().setTile(tile, this.newPosition);
        return new BuyLandResult(tile.getTiles(), price);
    }
}
