package edu.kit.informatik.game.elements;

import edu.kit.informatik.game.storages.MarketPriceList;
import edu.kit.informatik.game.storages.PriceLink;
import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.StringUtils;
import edu.kit.informatik.utils.TurnEndListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This is a core element of the game. The market lets the player sell vegetables in exchange for gold. After each
 * turn the markets price-list is updated, adjusting to the vegetables bought. Each pair of vegetables linked in the
 * price list, fore every two more vegetables of the first or second vegetable, the price point is moved up or down
 *
 * @author uzovo
 * @version 1.0
 */
public class Market {
    private final MarketPriceList marketPriceList;
    private final TurnEndListener turnEndListener;
    private final VegetableAmounts vegetablesSoldThisRound;

    /**
     * This instantiates a new market with the given price list, no sold vegetables and a turn end listener, updating
     * the markets prices after every turn
     * @param marketPriceList the price list of the market
     */
    public Market(final MarketPriceList marketPriceList) {
        this.marketPriceList = marketPriceList;
        this.vegetablesSoldThisRound = new VegetableAmounts();
        this.turnEndListener = new TurnEndListener(this::updateMarket);
    }

    /**
     * This copies the market with separate but equal attributes and no update function
     * @param market the market that should be copied
     */
    public Market(final Market market) {
        this.marketPriceList = new MarketPriceList(market.marketPriceList);
        this.vegetablesSoldThisRound = market.vegetablesSoldThisRound;
        this.turnEndListener = null;
    }

    /**
     * This sells the given amount of the given vegetable, returning the amount of gold that the vegetables were sold for
     * and increasing the vegetables sold this round
     * @param vegetables The vegetables that are to be sold
     * @param amount the amount to sell of the vegetable
     * @return the amount of gold that the vegetables are sold for
     */
    public int sellVegetables(final Vegetables vegetables, final int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount should not be bellow zero");
        this.vegetablesSoldThisRound.changeVegetableAmountBy(vegetables, amount);
        return this.marketPriceList.getPrices(vegetables) * amount;
    }

    /**
     * This sells the specified amounts of the specified vegetables
     * @param vegetableAmounts the vegetables with the amount of them, that should be sold
     * @return the amount of gold the vegetables are sold for
     */
    public int sellVegetables(final VegetableAmounts vegetableAmounts) {
        int totalPrice = 0;
        for (final Vegetables vegetable : Vegetables.values()) {
            totalPrice += this.sellVegetables(vegetable, vegetableAmounts.getAmount(vegetable));
        }
        return totalPrice;
    }

    /**
     * This returns the current selling price of the given vegetable
     * @param vegetable the vegetable whose price you want to know
     * @return the price of said vegetable
     */
    public int getSellingPrice(final Vegetables vegetable) {
        return this.marketPriceList.getPrices(vegetable);
    }


    private void updateMarket() {
        for (final PriceLink priceLink : this.marketPriceList.getLinks()) {
            this.marketPriceList.changePrice(priceLink,
                    (this.vegetablesSoldThisRound.getAmount(priceLink.vegetable2())
                            - this.vegetablesSoldThisRound.getAmount(priceLink.vegetable1())) / 2);
        }
        this.vegetablesSoldThisRound.resetAmounts();
    }

    private Collection<Vegetables> getVegetableListByMarketPriceList() {
        final Collection<Vegetables> result = new ArrayList<>();
        for (final PriceLink priceLink : this.marketPriceList.getLinks()) {
            result.add(priceLink.vegetable1());
            result.add(priceLink.vegetable2());
        }
        return result;
    }

    /**
     * This returns a list of vegetables sorted by the links on the market price list and their current prices
     * aligned to the right with one space between the longest vegetable and price
     * @return The specified string
     */
    @Override
    public String toString() {
        int wordLength = 0;
        int integerLength = 0;
        for (final Vegetables vegetable : Vegetables.values()) {
            wordLength = Math.max(wordLength, vegetable.getPlural().length() + Main.WORD_NUMBER_SEPERATOR.length());
            integerLength = Math.max(Integer.toString(this.getSellingPrice(vegetable)).length(), integerLength);
        }
        final int totalLength = wordLength + integerLength + Main.SPACE.length();
        final StringBuilder stringBuilder = new StringBuilder();
        final Collection<Vegetables> marketSort = this.getVegetableListByMarketPriceList();
        for (final Vegetables vegetable : marketSort) {
            final String front = vegetable.getPlural() + Main.WORD_NUMBER_SEPERATOR;
            final String back = Integer.toString(this.getSellingPrice(vegetable));
            stringBuilder.append(StringUtils.indentCorrectly(totalLength, front, back, Main.SPACE));
            stringBuilder.append(System.lineSeparator());
        }
        stringBuilder.delete(stringBuilder.length() - System.lineSeparator().length(), stringBuilder.length());
        return stringBuilder.toString();
    }
}
