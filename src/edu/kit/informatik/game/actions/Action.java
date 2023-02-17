package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.InvalidArgumentException;

public interface Action {
    ActionResult execute(Player player, Market market, TileScrambler tiles) throws InvalidArgumentException;
}
