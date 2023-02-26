package edu.kit.informatik.ui;

import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.actions.*;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.storages.TurnInformation;
import edu.kit.informatik.game.storages.VegetableAmounts;
import edu.kit.informatik.utils.StringUtils;
import edu.kit.informatik.utils.Vector2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum Command {
    SHOW_BARN("show barn") {
        @Override
        String execute(final Game game, final String[] lineParts) {
            //this method is here so the barn doesn't have to have a reference on the Player
            final Barn barn = game.getPlayersCurrentBarn();
            final VegetableAmounts storedVegetables = barn.getStoredVegetables();
            final StringBuilder stringBuilder = new StringBuilder("Barn");
            final String playerGold = Integer.toString(game.getPlayersCurrentGold());
            if (storedVegetables.isEmpty()) {
                return stringBuilder.append(System.lineSeparator())
                        .append(String.format("%s%s %d", Main.GOLD_NAME
                                , Main.WORD_NUMBER_SEPERATOR, game.getPlayersCurrentGold()))
                        .toString();
            }
            stringBuilder.append(String.format(" (spoils in %d turns)", barn.foodSpoilsIn()));
            stringBuilder.append(System.lineSeparator());
            final List<Vegetables> sortedVegetables = storedVegetables.vegetablesSortedByAmountAsc();
            final Collection<Vegetables> toRemove = new ArrayList<>();
            for (final Vegetables vegetable : sortedVegetables) {
                if (storedVegetables.getAmount(vegetable) == 0) toRemove.add(vegetable);
            }
            sortedVegetables.removeAll(toRemove);

            final int longestIntegerLength
                    = Math.max(playerGold.length(), Integer.toString(storedVegetables.getTotalNumberOfVegetables()).length());
            int longestWordLength = Main.GOLD_NAME.length() + Main.WORD_NUMBER_SEPERATOR.length();

            for (final Vegetables vegetable : sortedVegetables) {
                longestWordLength = Math.max(vegetable.getPlural().length() + Main.WORD_NUMBER_SEPERATOR.length()
                        , longestWordLength);
            }

            final int totalLength = longestIntegerLength + longestWordLength + Main.SPACE.length();

            for (final Vegetables vegetable : sortedVegetables) {
                final String front = vegetable.getPlural() + Main.WORD_NUMBER_SEPERATOR;
                final String back = Integer.toString(storedVegetables.getAmount(vegetable));
                stringBuilder.append(StringUtils.indentCorrectly(totalLength, front, back, Main.SPACE));
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.append(Main.LINE_PART.repeat(totalLength)).append(System.lineSeparator());
            stringBuilder.append(StringUtils.indentCorrectly(totalLength
                    , Main.SUM_INDICATOR + Main.WORD_NUMBER_SEPERATOR
                    , Integer.toString(storedVegetables.getTotalNumberOfVegetables()), Main.SPACE));
            stringBuilder.append(System.lineSeparator()).append(System.lineSeparator());
            stringBuilder.append(StringUtils.indentCorrectly(totalLength, Main.GOLD_NAME + Main.WORD_NUMBER_SEPERATOR, playerGold, Main.SPACE));
            return stringBuilder.toString();
        }
    },
    SHOW_BOARD("show board") {
        @Override
        String execute(final Game game, final String[] lineParts) {
            return game.getPlayersCurrentLand().toString();
        }
    },
    SHOW_MARKET("show market") {
        @Override
        String execute(final Game game, final String[] lineParts) {
            final Market market = game.getMarket();
            int wordLength = 0;
            int integerLength = 0;
            for (final Vegetables vegetable : Vegetables.values()) {
                wordLength = Math.max(wordLength, vegetable.getPlural().length() + Main.WORD_NUMBER_SEPERATOR.length());
                integerLength = Math.max(Integer.toString(market.getSellingPrice(vegetable)).length(), integerLength);
            }
            final int totalLength = wordLength + integerLength + Main.SPACE.length();
            final StringBuilder stringBuilder = new StringBuilder();
            final Collection<Vegetables> marketSort = market.getVegetableListByMarketPriceList();
            for (final Vegetables vegetable : marketSort) {
                final String front = vegetable.getPlural() + Main.WORD_NUMBER_SEPERATOR;
                final String back = Integer.toString(market.getSellingPrice(vegetable));
                stringBuilder.append(StringUtils.indentCorrectly(totalLength, front, back, Main.SPACE));
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.delete(stringBuilder.length() - System.lineSeparator().length(), stringBuilder.length());
            return stringBuilder.toString();
        }
    },
    SELL("sell(( (%s))+| all)?".formatted(Vegetables.getSingularRegex())) {
        @Override
        String execute(final Game game, final String[] lineParts) throws GameException {
            final VegetableAmounts vegetableAmounts = new VegetableAmounts();
            if(lineParts.length == LINE_PART_LENGTH_WITHOUT_ARGUMENTS){
                return game.performAction(new Sell(vegetableAmounts)).toString();
            }
            if (lineParts[ALL_LOCATION].equals("all")) {
                vegetableAmounts.addAll(game.getPlayersCurrentBarn().getStoredVegetables());
            } else {
                for (int i = BEGINNING_OF_VEGETABLE_LIST; i < lineParts.length; i++) {
                    vegetableAmounts.changeVegetableAmountBy(
                            Vegetables.fromSingular(lineParts[i]), 1);
                }
            }
            return game.performAction(new Sell(vegetableAmounts)).toString();
        }
    },
    BUY_VEGETABLE("buy (%s)".formatted(Vegetables.getSingularRegex())) {
        @Override
        String execute(Game game, String[] lineParts) throws GameException {
            Vegetables vegetable = Vegetables.fromSingular(lineParts[BEGINNING_OF_VEGETABLE_LIST]);
            return game.performAction(new BuyVegetable(vegetable)).toString();
        }
    },
    BUY_LAND("buy land (%s) (%s)".formatted(Main.INTEGER_REGEX, Main.INTEGER_REGEX)) {
        @Override
        String execute(Game game, String[] lineParts) throws GameException {
            Vector2d location = new Vector2d(lineParts[POSITION_OF_X_VALUE + OFFSET_BECAUSE_LAND]
                    , lineParts[POSITION_OF_Y_VALUE + OFFSET_BECAUSE_LAND]);
            return game.performAction(new BuyLand(location)).toString();
        }
    },
    HARVEST("harvest (%s) (%s) (%s)".formatted(Main.INTEGER_REGEX, Main.INTEGER_REGEX, Main.POSITIVE_INTEGER_REGEX)) {
        @Override
        String execute(Game game, String[] lineParts) throws GameException {
            Vector2d location = new Vector2d(lineParts[POSITION_OF_X_VALUE], lineParts[POSITION_OF_Y_VALUE]);
            int amount = Integer.parseInt(lineParts[POSITION_OF_AMOUNT_IN_LINE]);
            return game.performAction(new Harvest(location, amount)).toString();
        }
    },
    PLANT("plant (%s) (%s) (%s)".formatted(Main.INTEGER_REGEX, Main.INTEGER_REGEX, Vegetables.getSingularRegex())) {
        @Override
        String execute(Game game, String[] lineParts) throws GameException {
            Vector2d location = new Vector2d(lineParts[POSITION_OF_X_VALUE], lineParts[POSITION_OF_Y_VALUE]);
            Vegetables vegetable = Vegetables.fromSingular(lineParts[POSITION_OF_VEGETABLEF_IN_LINE]);
            return game.performAction(new Plant(location, vegetable)).toString();
        }
    },
    QUIT(Main.QUIT) {
        @Override
        String execute(Game game, String[] lineParts) throws GameException {
            game.quit();
            return null;
        }
    },
    NEXT("end turn") {
        @Override
        String execute(Game game, String[] lineParts) throws GameException {
            final TurnInformation turnInformation = game.nextTurn();
            if (!game.isRunning) return null;
            return turnInformation.toString();
        }
    };
    public static final int LINE_PART_LENGTH_WITHOUT_ARGUMENTS = 1;
    public static final int POSITION_OF_VEGETABLEF_IN_LINE = 3;
    public static final int POSITION_OF_AMOUNT_IN_LINE = 3;
    public static final int POSITION_OF_Y_VALUE = 2;
    public static final int POSITION_OF_X_VALUE = 1;
    public static final int OFFSET_BECAUSE_LAND = 1;
    public static final int BEGINNING_OF_VEGETABLE_LIST = 1;
    public static final int ALL_LOCATION = 1;

    String commandMatch;

    Command(final String commandMatch) {
        this.commandMatch = commandMatch;
    }

    public static final String executeCommand(Game game, String commandLine) throws GameException {
        for (Command command : values()) {
            if (commandLine.matches(command.commandMatch)) {
                return command.execute(game, commandLine.split(Main.SPACE));
            }
        }
        throw new GameException(ErrorMessage.NOT_A_COMMAND);
    }

    abstract String execute(Game game, String[] lineParts) throws GameException;
}
