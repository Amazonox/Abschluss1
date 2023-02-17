package edu.kit.informatik.game.interfaces;

public interface Scrambler<T> {
    T getNext();

    boolean isEmpty();
}
