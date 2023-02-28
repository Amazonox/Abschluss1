package edu.kit.informatik.ui.lexicology;

public record Verb(String thirdPersonSingular, String thirdPersonPlural) {
    public String thirdFromAmount(final int amount) {
        if (amount == 1) return this.thirdPersonSingular;
        if (amount >= 0) return this.thirdPersonPlural;
        else throw new IllegalArgumentException("amount should not be below 0");
    }
}
