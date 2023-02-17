package edu.kit.informatik.game.interfaces;

import java.util.Collection;

public interface Scrambler<T> {
    T getNext();
    boolean isEmpty();
}
