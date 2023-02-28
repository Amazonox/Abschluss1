package edu.kit.informatik.game.interfaces;

/**
 * This is an interface for objects only implementing the on player turn end method to be used by the turn end listener
 * class.
 */
public interface OnPlayerTurnEnd {
    /**
     * This method gets executed once a turn ends. This enables other classes to execute method once a turn ends.
     */
    void onPlayerTurnEnd();
}
