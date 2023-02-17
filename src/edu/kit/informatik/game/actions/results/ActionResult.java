package edu.kit.informatik.game.actions.results;

import edu.kit.informatik.game.storages.TurnInformation;

public abstract class ActionResult {
    private TurnInformation turnInformation;

     public void addTurnInformation(TurnInformation turnInformation) {
         this.turnInformation = turnInformation;
     }
    @Override
    public String toString() {
        if (this.turnInformation != null)
            return System.lineSeparator() + this.turnInformation.toString();
        return "";
    }
}
