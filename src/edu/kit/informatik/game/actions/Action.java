package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.GameException;

/**
 * this is an interface for all actions. Actions can be performed by a player to influence the game in many ways.
 * Every action is executable and upon execution should return an action result containing information about the
 * execution of said action
 *
 * @author uzovo
 * @version 1.0
 */
public interface Action {
    /**
     * this method executes an action, altering the game and allowing the player to progress. For subsequent processing
     * of the action, an action result is returned
     * @param player the player that is executing the action
     * @param market the market of this game
     * @param tiles the tile scrambler of this game
     * @return an action result containing information about the just performed action for subsequent processing
     * @throws GameException for when something in the game itself is wrong like the player not having enough money
     * or other recourses to perform this action
     */
    ActionResult execute(Player player, Market market, TileScrambler tiles) throws GameException;
}
