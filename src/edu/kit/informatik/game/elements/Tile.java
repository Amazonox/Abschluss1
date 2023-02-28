package edu.kit.informatik.game.elements;

import edu.kit.informatik.utils.Vector2d;

/**
 * This is an interface for all tiles, tiles can be held by the land and each tile belongs to a tile type
 */
public interface Tile {
    /**
     * The size all tiles need to produce when to string is executed
     */
    Vector2d TO_STRING_SIZE = new Vector2d(5, 3);

    /**
     * This returns the tile type this tile belongs to
     * @return The tile type this tile belongs to
     */
    TileType getTileType();

    /**
     * This copies a non updating version of the tile for read purposes, and so all tiles can be copied to copy maps
     * holding them.
     * @return A non updating version of this tile
     */
    Tile clone();
}
