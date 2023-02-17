package edu.kit.informatik.game.storages;

import edu.kit.informatik.ui.Main;

public record TurnInformation(String playerName, int vegetablesHaveGrown, boolean foodHasSpoiled) {
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Main.NEXT_TURN_PLAYER.formatted(this.playerName));
        if (this.vegetablesHaveGrown >= 1)
            stringBuilder.append(System.lineSeparator()).append(Main.NEXT_TURN_VEGETABLE_GROWTH.formatted(
                    this.vegetablesHaveGrown, Main.VEGETABLE_NOUN.fromAmount(this.vegetablesHaveGrown)));
        if (this.foodHasSpoiled)
            stringBuilder.append(System.lineSeparator()).append(Main.NEXT_TURN_BARN_SPOILED);
        return stringBuilder.toString();
    }
}
