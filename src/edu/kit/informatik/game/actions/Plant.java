package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.PlantResult;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.utils.Vector2d;

/**
 * This action lets the player plant one vegetable on a given tile. Each tile can only be cultivated if it is currently
 * empty
 *
 * @author uzovo
 * @version 1.0
 */
public class Plant implements Action {
    private final Vector2d tilePosition;
    private final Vegetables vegetable;

    /**
     * this instantiates a new plant action
     * @param tilePosition the tile the vegetable should be planted on
     * @param vegetable the vegetable that should be planted
     */
    public Plant(final Vector2d tilePosition, final Vegetables vegetable) {
        this.tilePosition = tilePosition;
        this.vegetable = vegetable;
    }

    @Override
    public ActionResult execute(Player player, Market market, TileScrambler tiles) throws GameException {
        final CultivatableTile tile = player.getLand().getCultivatableTile(this.tilePosition);
        final Barn barn = player.getLand().getBarn();
        if(!barn.containsVegetable(this.vegetable)) throw new GameException(ErrorMessage.NOT_ENOUGH_VEGETABLES);
        tile.plantVegetable(this.vegetable);
        barn.removeVegetable(this.vegetable);
        return new PlantResult();
    }
}
