package com.example.life.impl.field.initializer;

import com.example.life.api.field.Field;
import com.example.life.api.field.FieldInitializer;
import com.example.life.error.GameErrorCode;
import com.example.life.error.GameOfLifeException;
import com.example.life.model.Cell;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

public class TextMatrixFieldInitializer implements FieldInitializer {
    private int width;
    private int height;
    private List<String> lines;

    @Override
    public void readSource(String filePath) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(filePath);
        if (in == null) {
            throw new GameOfLifeException(GameErrorCode.INVALID_FILE,
                    new FileNotFoundException("Resource not found: " + filePath));
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            lines = br.lines().toList();

        } catch (IOException e) {
            throw new GameOfLifeException(GameErrorCode.INVALID_FILE, e);
        }
        height = lines.size();
        width = lines.isEmpty() ? 0 : lines.get(0).length();
    }

    @Override
    public void apply(Field field) {
        if (field.getWidth() != width || field.getHeight() != height) {
            throw new GameOfLifeException(GameErrorCode.INVALID_DIMENSIONS);
        }
        IntStream.range(0, height).forEach(y -> {
            String line = lines.get(y);
            IntStream.range(0, width).forEach(x ->
                    field.set(x, y, line.charAt(x) == '*' ? Cell.ALIVE : Cell.DEAD)
            );
        });
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
