package edu.kit.informatik.game.storages;

import edu.kit.informatik.ui.Main;

/**
 * This stores information about a turn. The player name, how many vegetables of this player have grown and if the
 * food in his barn has spoiled.
 * @param playerName The name of the player whose turn it is.
 * @param vegetablesHaveGrown The total number of vegetables that have grown the tiles of the player last round.
 * @param foodHasSpoiled True, if the food of this player has spoiled this last round, otherwise false.
 */
public record TurnInformation(String playerName, int vegetablesHaveGrown, boolean foodHasSpoiled) {
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder(System.lineSeparator());
        stringBuilder.append(Main.NEXT_TURN_PLAYER.formatted(this.playerName));
        if (this.vegetablesHaveGrown >= 1)
            stringBuilder.append(System.lineSeparator()).append(Main.NEXT_TURN_VEGETABLE_GROWTH.formatted(
                    this.vegetablesHaveGrown, Main.VEGETABLE_NOUN.fromAmount(this.vegetablesHaveGrown)
            ,Main.HAVE.thirdFromAmount(this.vegetablesHaveGrown)));
        if (this.foodHasSpoiled)
            stringBuilder.append(System.lineSeparator()).append(Main.NEXT_TURN_BARN_SPOILED);
        return stringBuilder.toString();
    }
}
