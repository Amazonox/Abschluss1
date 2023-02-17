package edu.kit.informatik.game.storages;

import edu.kit.informatik.ui.Main;

public record Noun(String singular, String plural) {
    public Noun(final String singular) {
        this(singular, singular + Main.STANDARD_PLURAL_ENDING);
    }

    public String fromAmount(final int amount) {
        if (amount == 1) return this.singular;
        if (amount >= 0) return this.plural;
        else throw new IllegalArgumentException("amount should not be below 0");
    }
}
