package edu.kit.informatik.game.elements;

public enum Vegetables {
    CARROT("C",1, "carrots"),
    SALAD("S",2, "salads"),
    TOMATO("T",3, "tomatoes"),
    MUSHROOM("M",4, "mushrooms")
    ;

    private final String abbreviation;
    private final int daysToGrow;
    private final String plural;

    Vegetables(final String abbreviation, final int daysToGrow, final String plural) {
        this.abbreviation = abbreviation;
        this.daysToGrow = daysToGrow;
        this.plural = plural;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public int getDaysToGrow() {
        return this.daysToGrow;
    }

    public String getPlural() {
        return this.plural;
    }
}
