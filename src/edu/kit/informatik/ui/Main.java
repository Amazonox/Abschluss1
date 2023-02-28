package edu.kit.informatik.ui;

import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.ui.lexicology.Noun;
import edu.kit.informatik.ui.lexicology.Verb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author uzovo
 * @author Thomas Weber
 * @author Moritz Gstuer
 */

public final class Main {
    public static final String NEXT_TURN_PLAYER = "It is %s's turn!";
    public static final String NEXT_TURN_VEGETABLE_GROWTH = "%d %s %s grown since your last turn.";
    public static final String NEXT_TURN_BARN_SPOILED = "The vegetables in your barn are spoiled.";
    public static final String GOLD_NAME = "Gold";
    public static final String WORD_NUMBER_SEPERATOR = ":";
    public static final String SUM_INDICATOR = "Sum";
    public static final String SPACE = " ";
    public static final String LINE_PART = "-";
    public static final String COLUMN_SEPARATOR = "|";
    public static final String STANDARD_PLURAL_ENDING = "s";
    public static final String ENDSCREEN_POINTS = "Player %d (%s): %d";
    public static final String ENDSCREEN_WINNER_END = " has won!";
    public static final String ENDSCREEN_WINNERS_END = " and %s have won!";
    public static final String ENDSCREEN_WINNERS_MIDDLE = ", %s";
    public static final Noun VEGETABLE_NOUN = new Noun("vegetable", "vegetables");
    //no leading zeros
    public static final String POSITIVE_INTEGER_REGEX = "0*[1-9]\\d*";
    public static final String POSITIVE_OR_0_INTEGER_STRING = "(%s)|0".formatted(POSITIVE_INTEGER_REGEX);
    public static final String NAME_REGEX = "[A-Za-z]+";
    public static final int INTEGER_LEANGTH_WITHOUT_FIRST = 9;
    public static Verb HAVE = new Verb("has","have");
    private static final String ERROR_STRING = "Error:";
    public static final String NO_COUNTER_INDICATOR = "*";
    //040 is space
    private static final String PIXEL_ART = """
                           _.-^-._    .--.\040\040\040\040
                        .-'   _   '-. |__|\040\040\040\040
                       /     |_|     \\|  |\040\040\040\040
                      /               \\  |\040\040\040\040
                     /|     _____     |\\ |\040\040\040\040
                      |    |==|==|    |  |\040\040\040\040
  |---|---|---|---|---|    |--|--|    |  |\040\040\040\040
  |---|---|---|---|---|    |==|==|    |  |\040\040\040\040
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^""";
    private static final String SETUP_NAME_COUNT = "How many players?";
    private static final String SETUP_PLAYER_NAMES = "Enter the name of player %d:";
    private static final String SETUP_START_GOLD = "With how much gold should each player start?";
    private static final String SETUP_END_GOLD = "With how much gold should a player win?";
    private static final String SETUP_SEED = "Please enter the seed used to shuffle the tiles:";
    public static final String BUY_RESULT_SCEMEATIC = "You have bought a %s for %d gold.";
    public static final Noun VEGETABEL = new Noun("vegetable");
    public static final String INTEGER_REGEX = "(-?%s)|0".formatted(POSITIVE_INTEGER_REGEX);
    public static final String QUIT = "quit";
    public static final String REGEX_OR = "|";
    public static final Noun TURN = new Noun("turn");
    public static boolean quit = false;
    private static final int FIRST_PLAYER_NUMBER = 1;

    private Main() {
    }

    public static void main(final String[] args) {
        if (args.length >= 1) {
            printErrorMessage(ErrorMessage.TOO_MANY_ARGUMENTS.getMessage());
            return;
        }
        final Scanner inputScanner = new Scanner(System.in);
        System.out.println(PIXEL_ART);
        final int playerCount = getIntegerFromInput(inputScanner
            , SETUP_NAME_COUNT,POSITIVE_INTEGER_REGEX,ErrorMessage.NOT_A_POSITIVE_INTEGER);
        if (quit) return;
        final List<String> playerNames = getPlayerListFromInput(playerCount, inputScanner);
        if (quit) return;
        final int startGold = getIntegerFromInput(inputScanner
            , SETUP_START_GOLD, POSITIVE_OR_0_INTEGER_STRING, ErrorMessage.NOT_A_POSITIVE_INTEGER);
        if (quit) return;
        final int endGold = getIntegerFromInput(inputScanner
            , SETUP_END_GOLD, POSITIVE_INTEGER_REGEX, ErrorMessage.NOT_A_POSITIVE_INTEGER);
        if (quit) return;
        final int seed = getIntegerFromInput(inputScanner
            ,SETUP_SEED,INTEGER_REGEX,ErrorMessage.NOT_A_INTEGER);
        if(quit) return;
        final Game game = new Game(playerNames, startGold, endGold, seed);
        System.out.println(game.firstTurn());
        while (!quit && game.isRunning) {
            final String input = inputScanner.nextLine();
            try {
                final String output = Command.executeCommand(game, input);
                if (output != null) {
                    System.out.println(output);
                }
            } catch (final GameException gameException) {
                printErrorMessage(gameException.getMessage());
            }
        }
        winScreen(game.getPlayerList(),endGold);

    }

    private static void printErrorMessage(final String message) {
        System.err.printf("%s %s%n", ERROR_STRING, message);
    }

    private static int getIntegerFromInput(final Scanner scanner, String text, final String match, ErrorMessage errorMessage) {
        System.out.println(text);
        final String input = scanner.nextLine();
        if (input.matches(QUIT)) {
            quit = true;
            return -1;
        }
        if (input.matches(match) && input.length() <= INTEGER_LEANGTH_WITHOUT_FIRST) {
            return Integer.parseInt(input);
        }
        printErrorMessage(errorMessage.getMessage());
        return getIntegerFromInput(scanner, text, match, errorMessage);
    }

    private static List<String> getPlayerListFromInput(final int count, final Scanner scanner) {
        return getPlayerListFromInput(FIRST_PLAYER_NUMBER, count, scanner);
    }

    private static List<String> getPlayerListFromInput(final int currentPlayer, final int totalPlayerCount, final Scanner scanner) {
        //prints the string with count instead of %d
        System.out.printf((SETUP_PLAYER_NAMES) + "%n", currentPlayer);
        final String input = scanner.nextLine();
        if (input.matches(QUIT)) {
            quit = true;
            return Collections.emptyList();
        }
        if (!input.matches(NAME_REGEX)) {
            printErrorMessage(ErrorMessage.NOT_A_NAME.getMessage());
            return getPlayerListFromInput(currentPlayer, totalPlayerCount, scanner);
        }
        if (currentPlayer == totalPlayerCount) return new ArrayList<>(List.of(input));
        final List<String> listOfFollowingPlayers = getPlayerListFromInput(currentPlayer + 1, totalPlayerCount, scanner);
        listOfFollowingPlayers.add(0, input);
        return listOfFollowingPlayers;
    }

    private static void winScreen(final List<Player> playerList, final int moneyToWin) {
        int winnersMoney = 0;
        List<Player> winners = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            final Player player = playerList.get(i);
            final int gold = Math.min(player.getGold(),moneyToWin);
            if (gold > winnersMoney) {
                winnersMoney = gold;
                winners = new ArrayList<>(List.of(player));
            } else if (gold == winnersMoney) {
                winners.add(player);
            }
            System.out.printf((ENDSCREEN_POINTS) + "%n", i + 1, player.getName(), player.getGold());
        }
        final StringBuilder winnerString = new StringBuilder(winners.remove(0).getName());
        if (winners.isEmpty()) {
            winnerString.append(ENDSCREEN_WINNER_END);
            System.out.println(winnerString);
            return;
        }
        for (int i = 0; i < winners.size() - 1; i++) {
            winnerString.append(ENDSCREEN_WINNERS_MIDDLE.formatted(winners.get(i).getName()));
        }
        winnerString.append(ENDSCREEN_WINNERS_END.formatted(winners.get(winners.size() - 1).getName()));
        System.out.println(winnerString);
    }
    // TODO: 26.02.2023 Look at integer matches

}
