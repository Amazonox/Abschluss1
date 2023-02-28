package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.TileType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * This tile scrambler stores and scrambles given tiles with the given seed. It only allows access to the next tile.
 */
public class TileScrambler{
    private final ListIterator<TileType> iterator;

    /**
     * This instantiates a new tile scrambler that shuffles the given tiles with the given seed.
     * @param tiles The tiles of this scrambler.
     * @param seed The seed the tiles should be scrambled with.
     */
    public TileScrambler(final Collection<TileType> tiles, final int seed) {
        final List<TileType> shuffleTiles = new ArrayList<>(tiles);
        Collections.shuffle(shuffleTiles, new Random(seed));
        this.iterator = shuffleTiles.listIterator();
    }

    /**
     * This returns the next tile in the scrambler.
     * @return The next tile in the scrambler.
     */
    public TileType getNext() {
        return this.iterator.next();
    }

    /**
     * This returns if the scrambler has run out of tiles.
     * @return True if there are no more tiles in the tile scrambler, otherwise false.
     */
    public boolean isEmpty() {
        return !this.iterator.hasNext();
    }
}
