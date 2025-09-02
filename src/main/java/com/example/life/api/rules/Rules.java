package com.example.life.api.rules;

import com.example.life.api.field.Field;
import com.example.life.model.Cell;

public interface Rules {
    Cell nextState(Field field, int x, int y);
}
