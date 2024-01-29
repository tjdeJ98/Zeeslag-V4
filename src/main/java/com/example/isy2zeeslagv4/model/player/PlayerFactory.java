package com.example.isy2zeeslagv4.model.player;

import com.example.isy2zeeslagv4.model.game.games.Game;
import com.example.isy2zeeslagv4.model.player.battleship.strategy.AIStrategy;

public interface PlayerFactory <T extends Game> {
    Player<T> createConsolePlayer(String name);
    Player<T> createAIPlayer(String name, AIStrategy strategy);
    Player<T> createUIPlayer(String name);
}
