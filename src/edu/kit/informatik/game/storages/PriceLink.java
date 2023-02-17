package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Vegetables;

public record PriceLink(Vegetables vegetable1, Vegetables vegetable2, int value) {
    public PriceLink {
        if (vegetable1 == vegetable2) throw new IllegalArgumentException("no same vegetables for price-link");
        if (value < 0) throw new IllegalArgumentException("value should not be below zero");
    }
}
