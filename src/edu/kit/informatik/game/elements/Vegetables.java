package edu.kit.informatik.game.elements;

import edu.kit.informatik.game.storages.Noun;
import edu.kit.informatik.ui.Main;

public enum Vegetables {
    CARROT("C", 1, new Noun("carrot")),
    SALAD("S", 2, new Noun("salad")),
    TOMATO("T", 3, new Noun("tomato", "tomatoes")),
    MUSHROOM("M", 4, new Noun("mushroom"));

    private final String abbreviation;
    private final int daysToGrow;
    private final Noun name;

    Vegetables(final String abbreviation, final int daysToGrow, final Noun name) {
        this.abbreviation = abbreviation;
        this.daysToGrow = daysToGrow;
        this.name = name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public int getDaysToGrow() {
        return this.daysToGrow;
    }

    public String getPlural() {
        return this.name.plural();
    }

    public String getSingular() {
        return this.name.singular();
    }

    public static String getSingularRegex() {
        StringBuilder regexSingulars = new StringBuilder();
        for (final Vegetables vegetable : values()) {
            regexSingulars.append(vegetable.getSingular());
            regexSingulars.append(Main.REGEX_OR);
        }
        regexSingulars.deleteCharAt(regexSingulars.length() - 1);
        return regexSingulars.toString();
    }

    public static Vegetables fromSingular(final String string) {
        for (final Vegetables vegetable : values()) {
            if (string.equals(vegetable.getSingular())) return vegetable;
        }
        throw new IllegalArgumentException("Not an vegetable");
    }

    public Noun getName() {
        return this.name;
    }
}
