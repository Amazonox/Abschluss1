package edu.kit.informatik.ui;

import edu.kit.informatik.game.Game;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.storages.Noun;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Scanner;

/**
 * @author uzovo
 * @author Thomas Weber
 * @author Moritz Gstuer
 */

public class Main {
    private static final String ERROR_STRING = "Error:";
    private static final String PIXEL_ART = """
                           _.-^-._    .--.
                        .-'   _   '-. |__|
                       /     |_|     \\|  |
                      /               \\  |
                     /|     _____     |\\ |
                      |    |==|==|    |  |
  |---|---|---|---|---|    |--|--|    |  |
  |---|---|---|---|---|    |==|==|    |  |
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^""";
    private static final String SETUP_NAME_COUNT = "How many players?";
    private static final String SETUP_PLAYER_NAMES = "Enter the name of player %d:";
    private static final String SETUP_START_GOLD = "With how much gold should each player start?";
    private static final String SETUP_END_GOLD = "With how much gold should a player win?";
    private static final String SETUP_SEED = "Please enter the seed used to shuffle the tiles:";

    public static final String NEXT_TURN_PLAYER = "It is %s turn!";
    public static final String NEXT_TURN_VEGETABLE_GROWTH = "%d %s has grown since your last turn.";
    public static final String NEXT_TURN_BARN_SPOILED = "The vegetables in your barn are spoiled.";
    public static final String GOLD_NAME = "Gold";
    public static final String WORD_NUMBER_SEPERATOR = ":";
    public static final String SUM_INDICATOR = "Sum";
    public static final String SPACE = " ";
    public static final String LINE_PART = "-";
    public static final String COLUMN_SEPARATOR = "|";

    public static final String ENDSCREEN_POINTS = "Player %d (%s): %d";
    public static final String ENDSCREEN_WINNER_END = " has won!";
    public static final String ENDSCREEN_WINNERS_END = " and %s have won!";
    public static final String ENDSCREEN_WINNERS_MIDDLE = ", %s";
    public static final Noun VEGETABLE_NOUN = new Noun("vegetable", "vegetables");
    //no leading zeros
    public static final String POSITIVE_INTEGER_REGEX = "[1-9]\\d*";
    private static final String INTEGER_REGEX = "-?"+POSITIVE_INTEGER_REGEX;
    public static final String NAME_REGEX = "[A-Za-z]+";
    private static final String QUIT = "quit";
    private static int FIRST_PLAYER_NUMBER = 1;
    public static boolean quit = false;



    public static void main(String[] args)
    {
        if(args.length > 1){
            printErrorMessage(ErrorMessage.TOO_MANY_ARGUMENTS.getMessage());
        }
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(PIXEL_ART);
        int playerCount = getPlayerCountFromInput(inputScanner);
        if(quit) return;
        List<String> playerNames = getPlayerListFromInput(playerCount,inputScanner);
        if(quit) return;
        int startGold = getStartGoldFromInput(inputScanner);
        if(quit) return;
        int endGold = getEndGoldFromInput(inputScanner);
        if(quit) return;
        int seed = getSeedFromInput(inputScanner);

        final Game game = new Game(playerNames,startGold,endGold,seed);
        System.out.println(game.firstTurn());
        while (!quit && game.isRunning){
            final String input = inputScanner.nextLine();
            try{
                final String output = Command.executeCommand(input,game);
                if(output != null){
                    System.out.println(output);
                }
            }catch (final InvalidArgumentException invalidArgumentException){
                printErrorMessage(invalidArgumentException.getMessage());
            }
        }
        winScreen(game.getPlayerList());

    }

    private static void printErrorMessage(final String message){
        System.err.println(String.format("%s %s",ERROR_STRING,message));
    }

    private static int getPlayerCountFromInput(final Scanner scanner){
        System.out.println(SETUP_NAME_COUNT);
        return getIntegerFromInput(scanner);
    }

    private static int getStartGoldFromInput(final Scanner scanner){
        System.out.println(SETUP_START_GOLD);
        return getIntegerFromInput(scanner);
    }

    private static int getEndGoldFromInput(final Scanner scanner){
        System.out.println(SETUP_END_GOLD);
        return getIntegerFromInput(scanner);
    }

    private static int getSeedFromInput(final Scanner scanner){
        System.out.println(SETUP_SEED);
        final String input = scanner.nextLine();
        if(input.matches(QUIT)) {
            quit = true;
            return -1;
        }
        if(input.matches(INTEGER_REGEX)) {
            return Integer.parseInt(input);
        }
        printErrorMessage(ErrorMessage.NOT_A_INTEGER.getMessage());
        return getPlayerCountFromInput(scanner);
    }

    private static int getIntegerFromInput(final Scanner scanner) {
        final String input = scanner.nextLine();
        if(input.matches(QUIT)) {
            quit = true;
            return -1;
        }
        if(input.matches(POSITIVE_INTEGER_REGEX) && input.length() < 10) {
            return Integer.parseInt(input);
        }
        printErrorMessage(ErrorMessage.NOT_A_POSITIVE_INTEGER.getMessage());
        return getPlayerCountFromInput(scanner);
    }

    private static List<String> getPlayerListFromInput(final int count, final Scanner scanner){
        return getPlayerListFromInput(FIRST_PLAYER_NUMBER,count,scanner);
    }

    private static List<String> getPlayerListFromInput(final int currentPlayer, final int totalPlayerCount, final Scanner scanner){
        //prints the string with count instead of %d
        System.out.printf((SETUP_PLAYER_NAMES) + "%n", currentPlayer);
        final String input = scanner.nextLine();
        if(input.matches(QUIT)) {
            quit = true;
            return Collections.emptyList();
        }
        if(!input.matches(NAME_REGEX)) {
            printErrorMessage(ErrorMessage.NOT_A_NAME.getMessage());
            return getPlayerListFromInput(currentPlayer, totalPlayerCount, scanner);
        }
        if(currentPlayer == totalPlayerCount) return List.of(input);
        final List<String> listOfFollowingPlayers = getPlayerListFromInput(currentPlayer + 1,totalPlayerCount,scanner);
        listOfFollowingPlayers.add(0,input);
        return listOfFollowingPlayers;
    }

    private static void winScreen(final List<Player> playerList){
        int winnersMoney = 0;
        List<Player> winners = new ArrayList<>();
        for(int i = 0; i< playerList.size();i++){
            final Player player = playerList.get(i);
            if(player.getGold() > winnersMoney){
                winnersMoney = player.getGold();
                winners = new ArrayList<>(List.of(player));
                System.out.println();
            } else if (player.getGold() == winnersMoney) {
                winners.add(player);
            }
            System.out.printf((ENDSCREEN_POINTS) + "%n", i+1,player.getName(),player.getGold());
        }
        final StringBuilder winnerString = new StringBuilder(winners.remove(0).getName());
        if(winners.isEmpty()) {
            winnerString.append(ENDSCREEN_WINNER_END);
            return;
        }
        for (int i = 0;i < winners.size() - 1;i++){
            winnerString.append(ENDSCREEN_WINNERS_MIDDLE.formatted(winners.get(i).getName()));
        }
        winnerString.append(ENDSCREEN_WINNERS_END.formatted(winners.get(winners.size() - 1).getName()));
        System.out.println(winnerString);
    }


}
