package com.example.isy2zeeslagv4.model.game.games;

import com.example.isy2zeeslagv4.model.player.Player;
import com.example.isy2zeeslagv4.model.player.battleship.BattleshipPlayerFactory;
import com.example.isy2zeeslagv4.model.player.battleship.strategy.AIStrategy;
import com.example.isy2zeeslagv4.model.player.battleship.strategy.RandomStrategy;
import com.example.isy2zeeslagv4.model.player.battleship.strategy.TacticalStrategy;
import com.example.isy2zeeslagv4.other.ExcelWriteRequest;
import com.example.isy2zeeslagv4.other.PlayerSetupType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BattleshipGame implements Game {
    public static final BlockingQueue<ExcelWriteRequest> writeQueue = new LinkedBlockingQueue<>();
    private BattleshipPlayerFactory playerFactory;
    private List<Player<BattleshipGame>> players;
    private boolean isOnline;
    Player<BattleshipGame> curPlayer;
    private int turnCount;
    private PlayerSetupType playersSetupType;
    private int[] shipSizes;

    public BattleshipGame(boolean isOnline, PlayerSetupType playerSetupType) {
        this.players = new ArrayList<>();
        this.playerFactory = new BattleshipPlayerFactory(this);
        this.isOnline = isOnline;
        this.playersSetupType = playerSetupType;
        this.shipSizes = new int[]{2, 4, 5, 6};
        turnCount = 0;
    }

    public Player<BattleshipGame> makeConsolePLayer(String name) {
        return playerFactory.createConsolePlayer(name);
    }

    public Player<BattleshipGame> makeAIPlayer(String name, AIStrategy strategy) {
        return playerFactory.createAIPlayer(name, strategy);
    }

    public Player<BattleshipGame> makeUIPlayer(String name) {
        return playerFactory.createUIPlayer(name);
    }

    @Override
    public void initialize() {
        if (turnCount==0) {
            switch (this.playersSetupType) {
                case PLAYER_VS_PLAYER:
                    players.add(makeConsolePLayer("Timo"));
                    players.add(makeConsolePLayer("Guest"));
                    break;
                case PLAYER_VS_AI:
                    players.add(makeConsolePLayer("Timo"));
                    players.add(makeAIPlayer("AI", new RandomStrategy()));
                    break;
                case AI_VS_AI:
                    players.add(makeAIPlayer("AI 1", new RandomStrategy()));
                    players.add(makeAIPlayer("AI 2", new TacticalStrategy(this)));
                    break;
                case PLAYER_VS_SERVER:
                    players.add(makeConsolePLayer("Timo"));
                    break;
                case AI_VS_SERVER:
                    players.add(makeAIPlayer("AI", new TacticalStrategy(this)));
                    break;
                default:
                    throw new IllegalArgumentException("This player setup is not available");
            }
            curPlayer = players.getFirst();
        }

        if (turnCount < 2) {
            curPlayer.initialize();
        }
    }

    @Override
    public void setupPhase() {
        System.out.println(curPlayer.getName());
        curPlayer.setupPhase();
    }

    @Override
    public void update() {
        if (turnCount > 1)
            curPlayer.makeMove();
    }

    @Override
    public boolean isGameOver() {
        return getOtherPlayer().isGameOver();
    }

    @Override
    public void endGame() {
        String file = "player_data.xlsx";
        ExcelWriteRequest request1 = new ExcelWriteRequest(curPlayer.getName(), getOtherPlayer().getMatchData(), file);
        ExcelWriteRequest request2 = new ExcelWriteRequest(getOtherPlayer().getName(), curPlayer.getMatchData(), file);

        writeQueue.add(request1);
        writeQueue.add(request2);
        curPlayer.endGame();
    }

    @Override
    public void switchPlayer() {
        if (curPlayer == players.getFirst()) {
            curPlayer = players.get(1);
        } else {
            curPlayer = players.getFirst();
        }
        turnCount++;
    }

    public static BlockingQueue<ExcelWriteRequest> getWriteQueue()
    {
        return writeQueue;
    }

    public Player<BattleshipGame> getOtherPlayer()
    {
        if (curPlayer == players.getFirst()) {
            return players.get(1);
        }
        return players.getFirst();
    }

    public Player<BattleshipGame> getCurPlayer()
    {
        return curPlayer;
    }

    public int[] getShipSizes()
    {
        return shipSizes;
    }
}
