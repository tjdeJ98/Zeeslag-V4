package com.example.isy2zeeslagv4.model.player.battleship;

import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.player.Player;
import com.example.isy2zeeslagv4.model.player.PlayerFactory;
import com.example.isy2zeeslagv4.model.player.battleship.strategy.AIStrategy;

public class BattleshipPlayerFactory implements PlayerFactory<BattleshipGame> {
    private BattleshipGame game;

    public BattleshipPlayerFactory(BattleshipGame game)
    {
        this.game = game;
    }
    @Override
    public Player<BattleshipGame> createConsolePlayer(String name) {
        return new ConsoleBattleshipPlayer(name, game);
    }

    @Override
    public Player<BattleshipGame> createAIPlayer(String name, AIStrategy strategy) {
        return new AIBattleshipPlayer(name, game, strategy);
    }

    @Override
    public Player<BattleshipGame> createUIPlayer(String name) {
        return new UIBattleshipPlayer(name, game);
    }
}
