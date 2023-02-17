package edu.kit.informatik.game.elements;

import edu.kit.informatik.utils.Vector2d;

public interface Tile {
    Vector2d TO_STRING_SIZE = new Vector2d(5, 3);

    Tiles getTiles();

    Tile clone();
}
