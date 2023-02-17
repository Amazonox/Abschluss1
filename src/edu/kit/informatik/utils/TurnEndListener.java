package edu.kit.informatik.utils;

import edu.kit.informatik.game.interfaces.OnPlayerTurnEnd;

import java.util.ArrayList;
import java.util.List;

public class TurnEndListener {
    private static List<TurnEndListener> turnEndListeners = new ArrayList<>();
    private final OnPlayerTurnEnd onPlayerTurnEnd;

    public TurnEndListener(final OnPlayerTurnEnd onPlayerTurnEnd) {
        this.onPlayerTurnEnd = onPlayerTurnEnd;
        turnEndListeners.add(this);
    }

    public static void endTurn() {
        for (final TurnEndListener turnEndListener : turnEndListeners) {
            turnEndListener.onPlayerTurnEnd.onPlayerTurnEnd();
        }
    }
}
