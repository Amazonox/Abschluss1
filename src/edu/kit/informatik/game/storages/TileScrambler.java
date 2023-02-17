package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.game.interfaces.Scrambler;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class TileScrambler implements Scrambler<Tiles> {
    List<Tiles> tiles;
    ListIterator<Tiles> iterator;

    public TileScrambler(final List<Tiles> tiles, final int seed){
        this.tiles.addAll(tiles);
        Collections.shuffle(tiles,new Random(seed));
        this.iterator = tiles.listIterator();
    }

    @Override
    public Tiles getNext() {
        return this.iterator.next();
    }

    @Override
    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }
}
