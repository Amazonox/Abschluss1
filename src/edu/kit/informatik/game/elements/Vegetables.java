package edu.kit.informatik.game.elements;

import edu.kit.informatik.ui.lexicology.Noun;
import edu.kit.informatik.ui.Main;

/**
 * These are the vegetables which can be planted in the game. Each vegetable has an abbreviation, a name and
 * a number of rounds it needs to grow.
 */
public enum Vegetables {
    /**
     * A carrot is abbreviated with C, needs 1 day to grow.
     */
    CARROT("C", 1, new Noun("carrot")),
    /**
     * A salad is abbreviated with S, needs 2 rounds to grow.
     */
    SALAD("S", 2, new Noun("salad")),
    /**
     * A tomato is abbreviated with T, needs 3 rounds to grow.
     */
    TOMATO("T", 3, new Noun("tomato", "tomatoes")),
    /**
     * A mushroom is abbreviated with m, needs 4 rounds to grow.
     */
    MUSHROOM("M", 4, new Noun("mushroom"));

    private final String abbreviation;
    private final int roundsToGrow;
    private final Noun name;

    Vegetables(final String abbreviation, final int roundsToGrow, final Noun name) {
        this.abbreviation = abbreviation;
        this.roundsToGrow = roundsToGrow;
        this.name = name;
    }

    /**
     * This returns the abbreviation of this vegetable.
     * @return The abbreviation of this vegetable as a string.
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * This returns the amount of rounds this vegetable needs to grow.
     * @return the amount of rounds this vegetable needs to grow.
     */
    public int getRoundsToGrow() {
        return this.roundsToGrow;
    }

    /**
     * This returns the plural name of this vegetable. For when there are multiple or none.
     * @return The plural name of this vegetable.
     */
    public String getPlural() {
        return this.name.plural();
    }

    /**
     * This returns the singular name of this vegetable. Use this if there is exactly one.
     * @return The singular name of this vegetable
     */
    public String getSingular() {
        return this.name.singular();
    }

    /**
     * This returns a regex that matches the singulars of the vegetables. Make sure to put braces around this.
     * For example "carrot" is matched.
     * @return A string regex that matches any one singular name of a vegetable.
     */
    public static String getSingularRegex() {
        final StringBuilder regexSingulars = new StringBuilder();
        for (final Vegetables vegetable : values()) {
            regexSingulars.append(vegetable.getSingular());
            regexSingulars.append(Main.REGEX_OR);
        }
        regexSingulars.deleteCharAt(regexSingulars.length() - 1);
        return regexSingulars.toString();
    }

    /**
     * This returns the vegetable whose singular name matches the specified string.
     * @param string The string that needs to be matched against the vegetable singular names.
     * @return The vegetable whose singular matches the specified string.
     */
    public static Vegetables fromSingular(final String string) {
        for (final Vegetables vegetable : values()) {
            if (string.equals(vegetable.getSingular())) return vegetable;
        }
        throw new IllegalArgumentException("Not an vegetable");
    }

    /**
     * This returns the name of this vegetable as a noun containing both singular and plural.
     * @return The name of this vegetable.
     */
    public Noun getName() {
        return this.name;
    }
}
