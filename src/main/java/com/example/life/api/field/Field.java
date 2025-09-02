package com.example.life.api.field;

import com.example.life.model.Cell;

public interface Field {
    int getWidth();
    int getHeight();
    Cell get(int x, int y);
    void set(int x, int y, Cell cell);
    Field newInstance();
}
