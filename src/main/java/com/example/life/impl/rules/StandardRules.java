package com.example.life.impl.rules;

import com.example.life.api.field.Field;
import com.example.life.api.rules.Rules;
import com.example.life.model.Cell;

import java.util.stream.IntStream;

public class StandardRules implements Rules {
    @Override
    public Cell nextState(Field field, int x, int y) {
        long aliveNeighbors = IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap(dx -> IntStream.rangeClosed(-1, 1)
                        .filter(dy -> dx != 0 || dy != 0)
                        .mapToObj(dy -> new int[]{x + dx, y + dy}))
                .filter(coord -> coord[0] >= 0 && coord[0] < field.getWidth()
                        && coord[1] >= 0 && coord[1] < field.getHeight())
                .filter(coord -> field.get(coord[0], coord[1]) == Cell.ALIVE)
                .count();

        Cell current = field.get(x, y);
        if (current == Cell.ALIVE) {
            return (aliveNeighbors == 2 || aliveNeighbors == 3) ? Cell.ALIVE : Cell.DEAD;
        } else {
            return (aliveNeighbors == 3) ? Cell.ALIVE : Cell.DEAD;
        }
    }
}
