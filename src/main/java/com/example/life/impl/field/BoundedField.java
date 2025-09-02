package com.example.life.impl.field;

import com.example.life.model.Coordinate;
import com.example.life.api.field.Field;
import com.example.life.error.GameErrorCode;
import com.example.life.error.GameOfLifeException;
import com.example.life.model.Cell;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BoundedField implements Field {
    private final int width;
    private final int height;
    private final Set<Coordinate> aliveCells;

    public BoundedField(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new GameOfLifeException(GameErrorCode.INVALID_DIMENSIONS);
        }
        this.width = width;
        this.height = height;
        this.aliveCells = new HashSet<>();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Cell get(int x, int y) {
        checkBounds(x, y);
        return aliveCells.contains(new Coordinate(x, y)) ? Cell.ALIVE : Cell.DEAD;
    }

    @Override
    public void set(int x, int y, Cell cell) {
        checkBounds(x, y);
        Coordinate coord = new Coordinate(x, y);
        if (cell == Cell.ALIVE) {
            aliveCells.add(coord);
        } else {
            aliveCells.remove(coord);
        }
    }

    public Set<Coordinate> getAliveCells() {
        return Collections.unmodifiableSet(aliveCells);
    }

    private void checkBounds(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new GameOfLifeException(GameErrorCode.OUT_OF_BOUNDS);
        }
    }

    @Override
    public Field newInstance() {
        return new BoundedField(width, height);
    }
}
