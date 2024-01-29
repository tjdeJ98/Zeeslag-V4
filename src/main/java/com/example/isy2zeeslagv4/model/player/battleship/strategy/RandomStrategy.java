package com.example.isy2zeeslagv4.model.player.battleship.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements AIStrategy {
    private List<Integer> availableShots;
    private int boardSize = 8;
    private Random random;

    @Override
    public void initialize() {
        createAvailableShots();
        this.random = new Random();
    }

    @Override
    public int takeShot()
    {
        return createRandomShot();
    }

    private int createRandomShot() {
        if (availableShots.isEmpty()) {
            throw new IllegalStateException("No more available shots.");
        }
        int shotIndex = random.nextInt(availableShots.size());
        int shot = availableShots.get(shotIndex);
        availableShots.remove(shotIndex);
        return shot;
    }

    private void createAvailableShots() {
        availableShots = new ArrayList<>();
        for (int i = 0; i < boardSize * boardSize; i++) {
            availableShots.add(i);
        }
    }
}
