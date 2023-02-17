package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.InvalidArgumentException;

public abstract class Action {
    protected Player player;
    protected Market market;

    public Action(final Player player, final Market market, final TileScrambler tiles) {
        this.player = player;
        this.market = market;
    }

    public abstract void execute() throws InvalidArgumentException;
}
