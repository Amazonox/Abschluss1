package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Vegetables;

/**
 * A price link, that links two vegetables and stores the price point for use in the market price list. Vegetables one and
 * two should be different and the price point needs to be non-negative.
 * @param vegetable1 The first vegetable to be linked with the second vegetable.
 * @param vegetable2 The second vegetable to be linked to the first vegetable.
 * @param pricePoint The price point these two vegetables should be at.
 */
public record PriceLink(Vegetables vegetable1, Vegetables vegetable2, int pricePoint) {
    /**
     * This instantiates a new price link making sure vegetables one and two ar different and the price point is non-negative.
     * @param vegetable1 The first vegetable, different from the second.
     * @param vegetable2 The second vegetable, different from the first.
     * @param pricePoint The price point, not bellow 0.
     */
    public PriceLink {
        if (vegetable1 == vegetable2) throw new IllegalArgumentException("no same vegetables for price-link");
        if (pricePoint < 0) throw new IllegalArgumentException("pricePoint should not be below zero");
    }
}
