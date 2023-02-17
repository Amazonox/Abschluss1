package edu.kit.informatik.game.elements;

import edu.kit.informatik.game.storages.PriceLink;
import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.utils.TurnEndListener;

import java.util.HashSet;
import java.util.Set;

public class Market {
    MarketPriceList marketPriceList;
    TurnEndListener turnEndListener;
    private final VegetableAmounts vegetablesSoldThisRound;


    public Market(final MarketPriceList marketPriceList) {
        this.marketPriceList = marketPriceList;
        this.vegetablesSoldThisRound = new VegetableAmounts();
        this.turnEndListener = new TurnEndListener(() -> this.updateMarket());
    }

    public Market(final Market market) {
        this.marketPriceList = new MarketPriceList(market.marketPriceList);
        this.vegetablesSoldThisRound = market.vegetablesSoldThisRound;
        this.turnEndListener = null;
    }


    public int sellVegetables(final Vegetables vegetables, final int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount should not be bellow zero");
        this.vegetablesSoldThisRound.changeVegetableAmountBy(vegetables, amount);
        return this.marketPriceList.getPrices(vegetables) * amount;
    }

    public int getSellingPrice(final Vegetables vegetable) {
        return this.marketPriceList.getPrices(vegetable);
    }

    public int sellVegetables(final VegetableAmounts vegetableAmounts) {
        int totalPrice = 0;
        for (final Vegetables vegetable : Vegetables.values()) {
            totalPrice += this.sellVegetables(vegetable, vegetableAmounts.getAmount(vegetable));
        }
        return totalPrice;
    }

    private void updateMarket() {
        for (final PriceLink priceLink : this.marketPriceList.getLinkes()) {
            this.marketPriceList.changePrice(priceLink,
                    (this.vegetablesSoldThisRound.getAmount(priceLink.vegetable2())
                            - this.vegetablesSoldThisRound.getAmount(priceLink.vegetable1())) % 2);
        }
        this.vegetablesSoldThisRound.resetAmounts();
    }

    public Set<Vegetables> getVegetableListbyMarketPriceList() {
        final Set<Vegetables> result = new HashSet<>();
        for (final PriceLink priceLink : this.marketPriceList.getLinkes()) {
            result.add(priceLink.vegetable1());
            result.add(priceLink.vegetable2());
        }
        return result;
    }
}
