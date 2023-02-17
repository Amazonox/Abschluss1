package edu.kit.informatik.game.storages;

public record Noun(String singular, String plural) {
    String fromAmount(final int amount){
        if(amount == 1) return this.singular;
        if(amount > 1) return this.plural;
        else throw new IllegalArgumentException("amount should not be below 0");
    }
}
