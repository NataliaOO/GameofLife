package com.example.life.core;

import com.example.life.api.field.Field;
import com.example.life.api.rules.Rules;

import java.util.stream.IntStream;

public class GameOfLife {
    private Field current;
    private final Rules rules;

    public GameOfLife(Field initial, Rules rules) {
        this.current = initial;
        this.rules = rules;
    }

    public void nextGeneration() {
        Field next = current.newInstance();
        IntStream.range(0, current.getWidth()).forEach(x ->
                IntStream.range(0, current.getHeight()).forEach(y ->
                        next.set(x, y, rules.nextState(current, x, y))
                )
        );
        this.current = next;
    }

    public Field getCurrentField() {
        return current;
    }
}
