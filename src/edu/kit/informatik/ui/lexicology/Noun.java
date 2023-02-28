package edu.kit.informatik.ui.lexicology;

import edu.kit.informatik.ui.Main;

/**
 * A noun is a basic term in Lexicology describing a thing. Each noun has both a singular and a plural.
 * The plural is most often formed by appending an s to the singular form but there are exceptions. The plural should
 * be use when describing zero or multiple of one thing. The singular is used for one.
 * @param singular The singular form of this noun
 * @param plural The plural form of this noun
 */
public record Noun(String singular, String plural) {
    /**
     * This instantiates a new noun from the specified singular by building the plural by appending the standard plural
     * ending to the singular.
     * @param singular The singular of this noun
     */
    public Noun(final String singular) {
        this(singular, singular + Main.STANDARD_PLURAL_ENDING);
    }

    /**
     * This returns the appropriate form of the noun for the given amount. Singular for one, Plural for less or more.
     * @param amount The amount of the thing this noun is associated with. Non-negative.
     * @return The singular if amount is one, otherwise the plural.
     */
    public String fromAmount(final int amount) {
        if (amount == 1) return this.singular;
        if (amount >= 0) return this.plural;
        else throw new IllegalArgumentException("amount should not be below 0");
    }
}
