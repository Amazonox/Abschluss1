package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Vegetables;

import java.util.*;

/**
 * This class holds each vegetable as well as an amount associated with these vegetables.
 */
public class VegetableAmounts {
    private final Map<Vegetables, Integer> vegetableAmounts;

    /**
     * This instantiates a new vegetable amounts with all vegetables amounts set to 0
     */
    public VegetableAmounts() {
        this.vegetableAmounts = new EnumMap<>(Vegetables.class);
        this.resetAmounts();
    }

    /**
     * This instantiates a new vegetable amounts with all ethe vegetable amounts set to the same value as specified
     * in the given vegetable amounts.
     * @param vegetableAmounts The vegetables whose amounts should also be in this one.
     */
    public VegetableAmounts(final VegetableAmounts vegetableAmounts) {
        this.vegetableAmounts = new EnumMap<>(vegetableAmounts.vegetableAmounts);
    }

    /**
     * This changes the amount of the given vegetable by the given amount. If the amount reached an amount below 0,
     * False is returned and the amount is not changed.
     * @param vegetable The vegetable whose amount is to be changed.
     * @param amount The amount the vegetables amount should be changed by.
     * @return True if the amount was changed, otherwise false.
     */
    public boolean changeVegetableAmountBy(final Vegetables vegetable, final int amount) {
        if (this.vegetableAmounts.get(vegetable) + amount < 0) return false;
        this.vegetableAmounts.put(vegetable, this.vegetableAmounts.get(vegetable) + amount);
        return true;
    }

    /**
     * This returns the amount of the given vegetable.
     * @param vegetable The vegetable whose amount needs to be known.
     * @return The amount of said vegetable.
     */
    public int getAmount(final Vegetables vegetable) {
        return this.vegetableAmounts.get(vegetable);
    }

    /**
     * This changes all the amounts to 0.
     */
    public void resetAmounts() {
        for (final Vegetables vegetable : Vegetables.values()) {
            this.vegetableAmounts.put(vegetable, 0);
        }
    }

    /**
     * This returns if all the amounts are set to 0.
     * @return True if all the amounts associated with the vegetables are 0, otherwise false.
     */
    public boolean isEmpty() {
        for (final Integer integer : this.vegetableAmounts.values()) {
            if (integer > 0) return false;
        }
        return true;
    }

    /**
     * This increments the amount of each vegetable by one for each time it is contained in the specified iterable.
     * @param vegetables The vegetables that should be added to the amounts.
     */
    public void addAll(final Iterable<Vegetables> vegetables) {
        for (final Vegetables vegetable : vegetables) {
            this.changeVegetableAmountBy(vegetable, 1);
        }
    }

    /**
     * This adds all the vegetables in the given vegetable amounts to this vegetable amounts essentially increasing each
     * amount of each vegetable in this vegetable amounts by the amount associated to the vegetable in the specified
     * vegetable amounts.
     * @param vegetableAmounts The amounts the number of these vegetables should be increased by.
     */
    public void addAll(final VegetableAmounts vegetableAmounts) {
        for (final Vegetables vegetable : Vegetables.values()) {
            this.changeVegetableAmountBy(vegetable, vegetableAmounts.getAmount(vegetable));
        }
    }

    /**
     * This returns the total number of vegetables in this vegetable amounts.
     * @return The amount of each vegetable added up.
     */
    public int getTotalNumberOfVegetables() {
        int amount = 0;
        for (final Integer integer : this.vegetableAmounts.values()) {
            amount += integer;
        }
        return amount;
    }

    /**
     * This sorts the vegetables by their amount returning a list with the vegetable with the largest amount in the
     * front and the least in the back. If two vegetables have the same amount, they are sorted alphabetically by
     * their plural.
     * @return A list of vegetables sorted in ascending order of amount.
     */
    public List<Vegetables> vegetablesSortedByAmountAsc() {
        final List<Vegetables> vegetablesByAmount = new ArrayList<>();
        boolean wasAdded = false;
        for (final Vegetables vegetable : Vegetables.values()) {
            for (int i = 0; i < vegetablesByAmount.size(); i++) {
                final Vegetables vegetableInList = vegetablesByAmount.get(i);
                if (this.getAmount(vegetableInList) == this.getAmount(vegetable)) {
                    if (vegetable.getPlural().compareTo(vegetableInList.getPlural()) < 0) {
                        vegetablesByAmount.add(i, vegetable);
                        wasAdded = true;
                        break;
                    }
                } else if (this.getAmount(vegetable) < this.getAmount(vegetableInList)) {
                    vegetablesByAmount.add(i, vegetable);
                    wasAdded = true;
                    break;
                }
            }
            if (!wasAdded) {
                vegetablesByAmount.add(vegetable);
            }
            wasAdded = false;
        }
        return vegetablesByAmount;
    }
}
