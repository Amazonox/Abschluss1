package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.storages.TurnInformation;

/**
 * This class contains the Action result parent for other action results to inherit. Its purpose is to be able to
 * add turn information to an action result when the turn ended after executing the action
 *
 * @author uzovo
 * @version 1.0
 */
public abstract class ActionResult {
    private TurnInformation turnInformation = null;

    /**
     * this adds turn information to an action result for when the turn ended on execution the action
     *
     * @param turnInformation the turn switch information containing infos about the next turn
     */
    public void addTurnInformation(final TurnInformation turnInformation) {
        this.turnInformation = turnInformation;
    }

    @Override
    public String toString() {
        if (this.turnInformation != null)
            return System.lineSeparator() + this.turnInformation.toString();
        return "";
    }
}
