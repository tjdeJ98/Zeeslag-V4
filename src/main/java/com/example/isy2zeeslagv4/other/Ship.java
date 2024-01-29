package com.example.isy2zeeslagv4.other;

import java.util.List;

public class Ship {
    private final int id;
    private final int size;
    private List<Integer> coordinates;
    private int health;

    public Ship(int shipId, int size)
    {
        this.id = shipId;
        this.size = size;
        this.health = size;
    }

    public void setCoordinates(List<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Integer> getCoordinates() {
        return coordinates;
    }

    public void takeHit()
    {
        health--;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }
}