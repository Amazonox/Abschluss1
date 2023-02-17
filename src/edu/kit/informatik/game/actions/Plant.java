package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.actions.results.PlantResult;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.utils.Vector2d;

public class Plant implements Action {
    private final Vector2d tilePosition;
    private final Vegetables vegetable;

    public Plant(final Vector2d tilePosition, final Vegetables vegetable) {
        this.tilePosition = tilePosition;
        this.vegetable = vegetable;
    }

    @Override
    public ActionResult execute(Player player, Market market, TileScrambler tiles) throws InvalidArgumentException {
        final CultivatableTile tile = player.getLand().getTile(this.tilePosition);
        final Barn barn = player.getLand().getBarn();
        barn.removeVegetable(this.vegetable);
        tile.plantVegetable(this.vegetable);
        return new PlantResult();
    }
}
