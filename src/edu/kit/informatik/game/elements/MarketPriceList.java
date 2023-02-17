package edu.kit.informatik.game.elements;

import edu.kit.informatik.game.storages.PriceLink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MarketPriceList {
    private final Map<Vegetables, int[]> marketPrices;
    private final Collection<PriceLink> priceLinks;

    public MarketPriceList(final Map<Vegetables, int[]> marketPrices, final Collection<PriceLink> priceLinks) {
        this.marketPrices = new HashMap<>(marketPrices);
        this.priceLinks = new ArrayList<>(priceLinks);
        for (final PriceLink priceLink : this.priceLinks) {
            if (marketPrices.get(priceLink.vegetable1()).length != marketPrices.get(priceLink.vegetable1()).length)
                throw new IllegalArgumentException("All vegetables linked should have the same number of prices");
            if (priceLink.value() >= marketPrices.get(priceLink.vegetable1()).length
                    || priceLink.value() >= marketPrices.get(priceLink.vegetable2()).length
            ) throw new IllegalArgumentException("StartPrice indicator above a vegetables count of prices");
        }
    }

    public MarketPriceList(final MarketPriceList marketPriceList) {
        this.marketPrices = new HashMap<>(marketPriceList.marketPrices);
        this.priceLinks = marketPriceList.priceLinks;
    }

    public int getPrices(final Vegetables vegetable) {
        final PriceLink priceLink = this.getPriceLink(vegetable);
        return this.marketPrices.get(vegetable)[priceLink.value()];
    }

    private PriceLink getPriceLink(final Vegetables vegetable) {
        for (final PriceLink priceLink : this.priceLinks) {
            if (priceLink.vegetable1() == vegetable || priceLink.vegetable2() == vegetable) {
                return priceLink;
            }
        }
        throw new IllegalArgumentException("vegetable not in any link");
    }

    public Collection<PriceLink> getLinkes() {
        return new ArrayList<>(this.priceLinks);
    }

    public void changePrice(final PriceLink priceLink, final int amount) {
        this.priceLinks.remove(priceLink);
        this.priceLinks.add(new PriceLink(priceLink.vegetable1(), priceLink.vegetable2(),
                //caps the value between 0 and the price list length
                Math.min(this.marketPrices.get(priceLink.vegetable1()).length - 1, Math.max(0, priceLink.value() + amount))));

    }

}
