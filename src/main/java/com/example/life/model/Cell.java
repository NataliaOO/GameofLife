package com.example.life.model;

public enum Cell {
    ALIVE, DEAD;

    public boolean isAlive() {
        return this == ALIVE;
    }
}
