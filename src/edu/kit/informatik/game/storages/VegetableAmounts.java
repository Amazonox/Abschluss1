package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Vegetables;

import java.util.*;

public class VegetableAmounts {
    Map<Vegetables, Integer> vegetableAmounts;

    public VegetableAmounts() {
        this.vegetableAmounts = new HashMap<>();
        this.resetAmounts();
    }

    public VegetableAmounts(final VegetableAmounts vegetableAmounts) {
        this.vegetableAmounts = new HashMap<>(vegetableAmounts.vegetableAmounts);
    }

    public VegetableAmounts(final Collection<Vegetables> vegetables) {
        new VegetableAmounts();
        this.addAll(vegetables);
    }

    public boolean changeVegetableAmountBy(final Vegetables vegetable, final int amount) {
        if (this.vegetableAmounts.get(vegetable) + amount < 0) return false;
        this.vegetableAmounts.put(vegetable, this.vegetableAmounts.get(vegetable) + amount);
        return true;
    }

    public int getAmount(final Vegetables vegetable) {
        return this.vegetableAmounts.get(vegetable);
    }

    public void resetAmounts() {
        for (final Vegetables vegetable : Vegetables.values()) {
            this.vegetableAmounts.put(vegetable, 0);
        }
    }

    public boolean isEmpty() {
        for (final Integer integer : this.vegetableAmounts.values()) {
            if (integer > 0) return false;
        }
        return true;
    }

    public void addAll(final Collection<Vegetables> vegetables) {
        for (final Vegetables vegetable : vegetables) {
            this.changeVegetableAmountBy(vegetable, 1);
        }
    }
    public void addAll(final VegetableAmounts vegetableAmounts) {
        for (final Vegetables vegetable : Vegetables.values()) {
            this.changeVegetableAmountBy(vegetable, vegetableAmounts.getAmount(vegetable));
        }
    }

    public int getTotalNumberOfVegetables() {
        int amount = 0;
        for (final Integer integer : this.vegetableAmounts.values()) {
            amount += integer;
        }
        return amount;
    }

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
