package edu.kit.informatik.game;
import edu.kit.informatik.game.actions.Actions;
import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Market;
import edu.kit.informatik.game.elements.MarketPriceList;
import edu.kit.informatik.game.elements.Tile;
import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.game.elements.Vegetables;
import edu.kit.informatik.game.interfaces.Scrambler;
import edu.kit.informatik.game.storages.Land;
import edu.kit.informatik.game.storages.PriceLink;
import edu.kit.informatik.game.storages.TileScrambler;
import edu.kit.informatik.game.storages.TurnInformation;
import edu.kit.informatik.utils.Counter;
import edu.kit.informatik.utils.TurnEndListener;
import edu.kit.informatik.utils.Vector2d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final List<Player> playerList;
    private Scrambler<Tiles> tilesScrambler;
    private int currentPlayer;
    private int actionsPerformed;

    public boolean isRunning;
    private final int endGold;
    private Market market;
    private static final List<Vegetables> START_VEGETABLES= List.of(Vegetables.CARROT, Vegetables.TOMATO, Vegetables.SALAD,
        Vegetables.MUSHROOM);
    private static final Vector2d BARN_LOCATION = new Vector2d(0,0);
    private static final Map<Vector2d, Tile> START_MAP = Map.of(
        BARN_LOCATION,
        Tiles.BARN.getTile(),
        new Vector2d(-1,0),
        Tiles.GARDEN.getTile(),
        new Vector2d(1,0),
        Tiles.GARDEN.getTile(),
        new Vector2d(0,1),
        Tiles.FIELD.getTile());
    private static final MarketPriceList MARKET_PRICE_LIST = new MarketPriceList(
        Map.of(Vegetables.MUSHROOM, new int[] {12, 15, 16, 17, 20},
                Vegetables.CARROT,new int[]{3,2,2,2,1},
                Vegetables.TOMATO,new int[]{3,5,6,7,9},
                Vegetables.SALAD,new int[]{6,5,4,3,2}),
        List.of(new PriceLink(Vegetables.MUSHROOM,Vegetables.CARROT,2),
                new PriceLink(Vegetables.TOMATO,Vegetables.SALAD,2))
        );



    public Game(List<String> player, int startGold, int endGold, int seed) {
        this.playerList = new ArrayList<>();
        this.market = new Market(MARKET_PRICE_LIST);
        this.endGold = endGold;
        this.tilesScrambler = this.scramblerTiles(seed);
        this.gameStart(startGold,player,endGold);
        this.currentPlayer = 0;
        isRunning = true;
    }

    private void gameStart(final int startGold, final List<String> playerNames,final int endGold){
        for (final String name: playerNames) {
        this.playerList.add(new Player(name,startGold,new Land(new HashMap<>(START_MAP),BARN_LOCATION),START_VEGETABLES));
        }
    }

    public TurnInformation firstTurn(){
        return new TurnInformation(this.playerList.get(this.currentPlayer).getName(),
            0,false);
    }

    public TurnInformation nextTurn(){
        TurnEndListener.endTurn();
        this.currentPlayer++;
        this.actionsPerformed = 0;
        if(this.currentPlayer == this.playerList.size()) {
            this.updateRound();
        }
        final Player playerThisTurn = this.playerList.get(this.currentPlayer);
        return new TurnInformation(playerThisTurn.getName()
            ,playerThisTurn.getLand().getNumberOfVegetablesGrown()
            ,playerThisTurn.getLand().hasFoodSpoiled());
    }

    private void updateRound(){
        Counter.updateCounters();
        if(this.hasPlayerWon()) this.isRunning = false;
        this.currentPlayer = 0;
    }

    private Scrambler<Tiles> scramblerTiles(int seed){
        return new TileScrambler(this.getTilesInGameAfterStart(),seed);
    }

    private List<Tiles> getTilesInGameAfterStart(){
        final List<Tiles> tiles = new ArrayList<>();
        for (final Tiles tile : Tiles.values()){
            for (int i = 0; i< tile.getTimesInGamePerPlayer() * this.playerList.size(); i++){
                tiles.add(tile);
            }
        }
        for (final Tile tile : START_MAP.values()){
            tiles.remove(tile.getTiles());
        }
        return tiles;
    }

    private boolean hasPlayerWon(){
        for (Player player : playerList){
            if(player.getGold() >= this.endGold)
                return true;
        }
        return false;
    }

    public List<Player> getPlayerList() {
        return new ArrayList<>(playerList);
    }

    public Barn getPlayersCurrentBarn(){
        return new Barn(this.playerList.get(this.currentPlayer).getLand().getBarn());
    }
    public int getPlayersCurrentGold(){
        return this.playerList.get(this.currentPlayer).getGold();
    }

    public Land getPlayersCurrentLand(){
        return new Land(this.playerList.get(this.currentPlayer).getLand());
    }

    public Market getMarket(){
        return new Market(this.market);
    }
}
