package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.utils.Vector2d;

public class BuyLand extends Action{
    TileScrambler tileScrambler;
    Vector2d newPosition;

    public BuyLand(final Player player, final Market market,
                   final TileScrambler tileScrambler, final Vector2d newPosition) {
        super(player, market,tileScrambler);
        this.newPosition = newPosition;
    }

    @Override
    public void execute() throws InvalidArgumentException {
        int manhattanDistance = player.getLand().getManhattanDistanceForNewTile(this.newPosition);
        int price = 10*(manhattanDistance - 1);
        if(tileScrambler.isEmpty()) throw new InvalidArgumentException(ErrorMessage.NO_MORE_TILES_IN_GAME);
        player.spendGold(price);
        player.getLand().setTile(tileScrambler.getNext().getTile(), this.newPosition);

    }

}
