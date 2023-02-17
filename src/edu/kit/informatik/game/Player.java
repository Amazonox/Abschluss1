package edu.kit.informatik.game;

import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.Land;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;

import java.util.Collection;

public class Player {
    private int gold;
    private final Land land;

    private final String name;


    public Player(final String name,final int startGold, final Land land, final Collection<Vegetables> vegetables) {
        this.name = name;
        this.gold = startGold;
        this.land = land;
        this.land.getBarn().storeVegetables(vegetables);
    }

    public Player(final Player player){
        this.name = player.name;
        this.gold = player.gold;
        this.land = new Land(player.land);
    }

    public Land getLand() {
        return this.land;
    }

    public void spendGold(int amount) throws InvalidArgumentException {
        if(this.gold-amount < 0) throw new InvalidArgumentException(ErrorMessage.INSUFFICIENT_MONEY);
        this.gold -= amount;
    }

    public String getName() {
        return this.name;
    }

    public int getGold() {
        return this.gold;
    }
}
