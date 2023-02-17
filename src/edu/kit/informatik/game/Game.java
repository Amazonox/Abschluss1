package edu.kit.informatik.game;

import edu.kit.informatik.game.actions.Action;
import edu.kit.informatik.game.actions.results.ActionResult;
import edu.kit.informatik.game.elements.*;
import edu.kit.informatik.game.storages.Land;
import edu.kit.informatik.game.storages.PriceLink;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.game.storages.TurnInformation;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.utils.Counter;
import edu.kit.informatik.utils.TurnEndListener;
import edu.kit.informatik.utils.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static final List<Vegetables> START_VEGETABLES = List.of(Vegetables.CARROT, Vegetables.TOMATO, Vegetables.SALAD,
            Vegetables.MUSHROOM);
    private static final Vector2d BARN_LOCATION = new Vector2d(0, 0);
    private static final Map<Vector2d, Tile> START_MAP = Map.of(
            BARN_LOCATION,
            Tiles.BARN.getTile(),
            new Vector2d(-1, 0),
            Tiles.GARDEN.getTile(),
            new Vector2d(1, 0),
            Tiles.GARDEN.getTile(),
            new Vector2d(0, 1),
            Tiles.FIELD.getTile());
    private static final MarketPriceList MARKET_PRICE_LIST = new MarketPriceList(
            Map.of(Vegetables.MUSHROOM, new int[]{12, 15, 16, 17, 20},
                    Vegetables.CARROT, new int[]{3, 2, 2, 2, 1},
                    Vegetables.TOMATO, new int[]{3, 5, 6, 7, 9},
                    Vegetables.SALAD, new int[]{6, 5, 4, 3, 2}),
            List.of(new PriceLink(Vegetables.MUSHROOM, Vegetables.CARROT, 2),
                    new PriceLink(Vegetables.TOMATO, Vegetables.SALAD, 2))
    );
    private final List<Player> playerList;
    private final int endGold;
    private final TileScrambler tilesScrambler;
    private final Market market;
    public boolean isRunning;
    private int currentPlayer;
    private int actionsPerformed;
    private static final int MAX_ACTIONS = 2;


    public Game(final List<String> playerNames, final int startGold, final int endGold, final int seed) {
        this.playerList = new ArrayList<>();
        this.market = new Market(MARKET_PRICE_LIST);
        this.endGold = endGold;
        this.intPlayers(startGold, playerNames, endGold);
        this.tilesScrambler = this.scramblerTiles(seed);
        this.currentPlayer = 0;
        this.actionsPerformed = 0;
        this.isRunning = true;
    }

    private void intPlayers(final int startGold, final Iterable<String> playerNames, final int endGold) {
        for (final String name : playerNames) {
            this.playerList.add(
                    new Player(name, startGold, new Land(new HashMap<>(START_MAP), BARN_LOCATION), START_VEGETABLES));
        }
    }

    public TurnInformation firstTurn() {
        return new TurnInformation(this.playerList.get(this.currentPlayer).getName(),
                0, false);
    }

    public TurnInformation nextTurn() {
        TurnEndListener.endTurn();
        this.currentPlayer++;
        this.actionsPerformed = 0;
        if (this.currentPlayer == this.playerList.size()) {
            this.updateRound();
        }
        final Player playerThisTurn = this.playerList.get(this.currentPlayer);
        return new TurnInformation(playerThisTurn.getName()
                , playerThisTurn.getLand().getNumberOfVegetablesGrown()
                , playerThisTurn.getLand().hasFoodSpoiled());
    }

    private void updateRound() {
        Counter.updateCounters();
        if (this.hasPlayerWon()) this.isRunning = false;
        this.currentPlayer = 0;
    }

    private TileScrambler scramblerTiles(final int seed) {
        return new TileScrambler(this.getTilesInGameAfterStart(), seed);
    }

    private List<Tiles> getTilesInGameAfterStart() {
        final List<Tiles> tiles = new ArrayList<>();
        for (final Tiles tile : Tiles.values()) {
            for (int i = 0; i < tile.getTimesInGamePerPlayer() * this.playerList.size(); i++) {
                tiles.add(tile);
            }
        }
        for (final Tile tile : START_MAP.values()) {
            tiles.remove(tile.getTiles());
        }
        return tiles;
    }

    private boolean hasPlayerWon() {
        for (final Player player : this.playerList) {
            if (player.getGold() >= this.endGold)
                return true;
        }
        return false;
    }

    public List<Player> getPlayerList() {
        return new ArrayList<>(this.playerList);
    }

    public Barn getPlayersCurrentBarn() {
        return new Barn(this.playerList.get(this.currentPlayer).getLand().getBarn());
    }

    public int getPlayersCurrentGold() {
        return this.playerList.get(this.currentPlayer).getGold();
    }

    public Land getPlayersCurrentLand() {
        return new Land(this.playerList.get(this.currentPlayer).getLand());
    }

    public Market getMarket() {
        return new Market(this.market);
    }

    public ActionResult performAction(final Action action) throws InvalidArgumentException {
        final ActionResult actionResult
                = action.execute(this.playerList.get(this.currentPlayer), this.market, this.tilesScrambler);
        this.actionsPerformed++;
        if (this.actionsPerformed == MAX_ACTIONS) {
            TurnInformation turnInformation = (this.nextTurn());
            if (this.isRunning) {
                actionResult.addTurnInformation(turnInformation);
            }
        }
        return actionResult;
    }

    public void quit() {
        this.isRunning = false;
    }
}
