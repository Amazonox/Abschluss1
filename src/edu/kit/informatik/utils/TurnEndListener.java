package edu.kit.informatik.utils;

import edu.kit.informatik.game.interfaces.OnPlayerTurnEnd;

import java.util.List;

public class TurnEndListener {
    private static List<TurnEndListener> turnEndListeners;
    private OnPlayerTurnEnd onPlayerTurnEnd;
    public TurnEndListener(OnPlayerTurnEnd onPlayerTurnEnd) {
        this.onPlayerTurnEnd = onPlayerTurnEnd;
        turnEndListeners.add(this);
    }

    public static void endTurn(){
        for (final TurnEndListener turnEndListener: turnEndListeners){
            turnEndListener.onPlayerTurnEnd.onPlayerTurnEnd();
        }
    }
}
