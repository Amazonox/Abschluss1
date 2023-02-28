package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Vegetables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This is a List of vegetables and prices. Every two vegetables are linked in their price, their price point moves
 * up and down together.
 */
public class MarketPriceList {
    private final Map<Vegetables, int[]> marketPrices;
    private final Collection<PriceLink> priceLinks;

    /**
     * This instantiates a new market price list with the given vegetable prices. Both the market prices and
     * the price links need to contain one of each vegetable. The price position of the price links defines the starting
     * price of each vegetable in the link. The starting price can not be above the length of the market prices or below
     * zero. The market prices of each two linked vegetables need to be of equal length.
     * @param marketPrices The vegetables and the prices they can have.
     * @param priceLinks The vegetables whose price point is linked.
     */
    public MarketPriceList(final Map<Vegetables, int[]> marketPrices, final Collection<PriceLink> priceLinks) {
        this.marketPrices = new EnumMap<>(marketPrices);
        this.priceLinks = new ArrayList<>(priceLinks);
        if(!new HashSet<>(List.of(Vegetables.values())).containsAll(marketPrices.keySet()))
            throw new IllegalArgumentException("Not all vegetables are included in the market prices");
        final Collection<Vegetables> vegetableCheck= new HashSet<>();
        for (final PriceLink priceLink : this.priceLinks) {
            if (marketPrices.get(priceLink.vegetable1()).length != marketPrices.get(priceLink.vegetable2()).length)
                throw new IllegalArgumentException("All vegetables linked should have the same number of prices");
            if (priceLink.pricePoint() >= marketPrices.get(priceLink.vegetable1()).length
            ) throw new IllegalArgumentException("StartPrice indicator above a vegetables count of prices");
            if(vegetableCheck.contains(priceLink.vegetable1()) || vegetableCheck.contains(priceLink.vegetable2())){
                throw new IllegalArgumentException("Two price links contain the same vegetable");
            }
            vegetableCheck.add(priceLink.vegetable1());
            vegetableCheck.add(priceLink.vegetable2());
        }
        if(!vegetableCheck.containsAll(List.of(Vegetables.values())))
            throw new IllegalArgumentException("Not all vegetables are included in the price links");
    }

    /**
     * This copies the price list given so that they are separate but equal.
     * @param marketPriceList The market price list that needs to be copied.
     */
    public MarketPriceList(final MarketPriceList marketPriceList) {
        this.marketPrices = new EnumMap<>(marketPriceList.marketPrices);
        this.priceLinks = marketPriceList.priceLinks;
    }

    /**
     * This returns the current price of this vegetable as specified by the price list and the current price point
     * of the price link this vegetable belongs to.
     * @param vegetable The vegetable whose price needs to be known.
     * @return The price of the specified vegetable.
     */
    public int getPrices(final Vegetables vegetable) {
        final PriceLink priceLink = this.getPriceLink(vegetable);
        return this.marketPrices.get(vegetable)[priceLink.pricePoint()];
    }

    private PriceLink getPriceLink(final Vegetables vegetable) {
        for (final PriceLink priceLink : this.priceLinks) {
            if (priceLink.vegetable1() == vegetable || priceLink.vegetable2() == vegetable) {
                return priceLink;
            }
        }
        throw new IllegalArgumentException("vegetable not in any link");
    }

    /**
     * This returns all the price links of this market price list.
     * @return An array list of the price links of this market price list.
     */
    public Collection<PriceLink> getLinks() {
        return new ArrayList<>(this.priceLinks);
    }

    /**
     * This changes the price links price point by the given amount. The price point can not be bellow 0 or above
     * the price list of the vegetables effected by the price link.
     * @param priceLink The price link whose price point should be changed.
     * @param amount A positive or negative integer that the price point should be changed by.
     */
    public void changePrice(final PriceLink priceLink, final int amount) {
        this.priceLinks.remove(priceLink);
        this.priceLinks.add(new PriceLink(priceLink.vegetable1(), priceLink.vegetable2(),
                //caps the pricePoint between 0 and the price list length
                Math.min(this.marketPrices.get(priceLink.vegetable1()).length - 1, Math.max(0, priceLink.pricePoint() + amount))));

    }

}
