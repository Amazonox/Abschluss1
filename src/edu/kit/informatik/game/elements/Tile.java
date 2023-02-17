package edu.kit.informatik.game.elements;

import edu.kit.informatik.utils.Vector2d;

public interface Tile{
    Tiles getTiles();
    Tile clone();

    public static final Vector2d TO_STRING_SIZE = new Vector2d(5,3);
}
