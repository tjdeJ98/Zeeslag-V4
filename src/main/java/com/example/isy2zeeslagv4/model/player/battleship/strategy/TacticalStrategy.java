package com.example.isy2zeeslagv4.model.player.battleship.strategy;

import com.example.isy2zeeslagv4.model.game.commands.board.battleship.Battleshipboard;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import com.example.isy2zeeslagv4.model.game.games.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TacticalStrategy implements AIStrategy {
    BattleshipGame game;
    private boolean[] board;
    private boolean[] hits;
    private final int boardSize = 8;
    private List<Integer> madeShots;
    private List<Integer> availableShots;
    private List<Integer> targetModeShots;
    private Random random;
    private boolean isTargetMode;

    public TacticalStrategy(BattleshipGame game)
    {
        this.game = game;
    }

    @Override
    public void initialize() {
        this.board = new boolean[boardSize * boardSize];
        this.hits = new boolean[boardSize * boardSize];
        this.random = new Random();
        this.madeShots = new ArrayList<>();
        this.availableShots = new ArrayList<>();
        this.targetModeShots = new ArrayList<>();
        this.isTargetMode = false;
        createAvailableShots();
    }

    private void createAvailableShots() {
        for (int i = 0; i < boardSize * boardSize; i++) {
            if ((i / boardSize + i % boardSize) % 2 == 0) { // Checkerboard pattern
                availableShots.add(i);
            }
        }
    }

    private void createRound2AvailableShots() {
        for (int i = 0; i < boardSize * boardSize; i++) {
            if ((i / boardSize + i % boardSize) % 2 != 0) { // Checkerboard pattern
                if (!madeShots.contains(i))
                    availableShots.add(i);
            }
        }
    }

    @Override
    public int takeShot() {
        // Implementation details
        return createTacticalShot(); // Example return
    }

    private int createTacticalShot() {
        int shot;
        if (isTargetMode && !targetModeShots.isEmpty()) {
            // In target mode, shoot at surrounding spots of a hit
            shot = targetModeShots.remove(0);
        } else {
            // Otherwise, follow the checkerboard pattern
            if (availableShots.isEmpty()) {
                createRound2AvailableShots();
            }

            shot = availableShots.remove(random.nextInt(availableShots.size()));
        }

        processShotResult(shot, ((Battleshipboard) game.getOtherPlayer().getBoard()).cellContainsShip(shot));
        madeShots.add(shot);
        return shot;
    }

    private void processShotResult(int shot, boolean isHit) {
        board[shot] = true; // Mark as shot
        if (isHit) {
            hits[shot] = true; // Mark as hit
            if (!isTargetMode) {
                isTargetMode = true;
                addTargetModeShots(shot);
            }
        } else if (isTargetMode && targetModeShots.isEmpty()) {
            // If no more target mode shots, switch back to checkerboard
            isTargetMode = false;
        }
    }

    private void addTargetModeShots(int hit) {
        int row = hit / boardSize;
        int col = hit % boardSize;

        // Add surrounding shots if they're within bounds and not already shot
        if (row > 0 && !board[hit - boardSize]) targetModeShots.add(hit - boardSize);
        if (row < boardSize - 1 && !board[hit + boardSize]) targetModeShots.add(hit + boardSize);
        if (col > 0 && !board[hit - 1]) targetModeShots.add(hit - 1);
        if (col < boardSize - 1 && !board[hit + 1]) targetModeShots.add(hit + 1);
    }
}
