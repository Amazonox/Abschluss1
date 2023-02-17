package edu.kit.informatik.game.actions;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Tile;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.utils.Vector2d;

public class Plant extends Action{
    private Vector2d tilePosition;
    private Vegetables vegetable;


    public Plant(Player player, Market market, Vector2d tilePosition, Vegetables vegetable,final TileScrambler tiles) {
        super(player, market,tiles);
        this.tilePosition = tilePosition;
        this.vegetable = vegetable;
    }

    @Override
    public void execute() throws InvalidArgumentException {
        final CultivatableTile tile = this.player.getLand().getTile(this.tilePosition);
        final Barn barn = this.player.getLand().getBarn();
        barn.removeVegetable(this.vegetable);
        tile.plantVegetable(this.vegetable);
    }
}
