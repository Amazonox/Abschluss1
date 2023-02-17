package edu.kit.informatik.ui;

import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.MarketPriceList;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.VegetableAmounts;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public enum Command {
    SHOW_BARN("show barn"){
        @Override
        String execute(Game game) {
            //this method is here so the barn doesn't have to have a reference on the Player
            Barn barn = game.getPlayersCurrentBarn();
            VegetableAmounts storedVegetables = barn.getStoredVegetables();
            StringBuilder stringBuilder = new StringBuilder("Barn");
            String playerGold = Integer.toString(game.getPlayersCurrentGold());
            if(storedVegetables.isEmpty()) {
                return stringBuilder.append(System.lineSeparator())
                    .append(String.format("%s%s %d", Main.GOLD_NAME, Main.WORD_NUMBER_SEPERATOR, playerGold))
                    .toString();
            }
            stringBuilder.append(String.format(" (spoils in %d turns)", barn.foodSpoilsIn()));
            stringBuilder.append(System.lineSeparator());
            List<Vegetables> sortedVegetables = storedVegetables.vegetablesSortedByAmountAsc();
            List<Vegetables> toRemove = new ArrayList<>();
            for (Vegetables vegetable: sortedVegetables){
                if(storedVegetables.getAmount(vegetable) == 0) toRemove.add(vegetable);
            }
            sortedVegetables.removeAll(toRemove);

            int longestIntegerLength
                = Math.max(playerGold.length(), Integer.toString(storedVegetables.getTotalNumberOfVegetables()).length());
            int longestWordLength = Main.GOLD_NAME.length() + Main.WORD_NUMBER_SEPERATOR.length();

            for (Vegetables vegetable : sortedVegetables){
                longestWordLength = Math.max(vegetable.getPlural().length() + Main.WORD_NUMBER_SEPERATOR.length()
                    ,longestWordLength);
            }

            int totalLength = longestIntegerLength + longestWordLength + Main.SPACE.length();

            for (Vegetables vegetable : sortedVegetables){
                String front = vegetable.getPlural() + Main.WORD_NUMBER_SEPERATOR;
                String back = Integer.toString(storedVegetables.getAmount(vegetable));
                stringBuilder.append(Command.indentCorrectly(totalLength,front,back,Main.SPACE));
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.append(Main.LINE_PART.repeat(totalLength)).append(System.lineSeparator());
            stringBuilder.append(Command.indentCorrectly(totalLength
                ,Main.SUM_INDICATOR + Main.WORD_NUMBER_SEPERATOR
                ,Integer.toString(storedVegetables.getTotalNumberOfVegetables()),Main.SPACE));
            stringBuilder.append(System.lineSeparator()).append(System.lineSeparator());
            stringBuilder.append(Command.indentCorrectly(totalLength,Main.GOLD_NAME+Main.WORD_NUMBER_SEPERATOR,playerGold,Main.SPACE));
            return stringBuilder.toString();
        }
    },
    SHOW_BOARD("show board"){
        @Override
        String execute(Game game) {
            return game.getPlayersCurrentLand().toString();
        }
    },
    SHOW_MARKET("show market") {
        @Override
        String execute(Game game) {
            Market market = game.getMarket();
            int wordLength = 0;
            int integerLength = 0;
            for (Vegetables vegetable: Vegetables.values()){
                wordLength = Math.max(wordLength,vegetable.getPlural().length() + Main.WORD_NUMBER_SEPERATOR.length());
                integerLength = Math.max(Integer.toString(market.getSellingPrice(vegetable)).length(),integerLength);
            }
            int totalLength = wordLength + integerLength + Main.SPACE.length();
            StringBuilder stringBuilder = new StringBuilder();
            Set<Vegetables> marketSort= market.getVegetableListbyMarketPriceList();
            for (Vegetables vegetable : marketSort){
                String front = vegetable.getPlural() + Main.WORD_NUMBER_SEPERATOR;
                String back = Integer.toString(market.getSellingPrice(vegetable));
                stringBuilder.append(Command.indentCorrectly(totalLength,front,back,Main.SPACE));
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.delete(stringBuilder.length()-System.lineSeparator().length(),stringBuilder.length());
            return stringBuilder.toString();
        }
    },
    SELL("sell ["+ )
    ;
    String commandMatch;
    private static final int BEGINNING_OF_VEGETABLE_LIST = 1;
    Command(final String commandMatch) {
        this.commandMatch = commandMatch;
    }

    private static String indentCorrectly(int totalLength,String front, String back,String space){
        StringBuilder lineStringBuilder = new StringBuilder(front);
        while (lineStringBuilder.length() + back.length() < totalLength){
            lineStringBuilder.append(space);
        }
        lineStringBuilder.append(back);
        return lineStringBuilder.toString();
    }
    abstract String execute(Game game);
}
