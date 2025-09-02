package com.example.life.impl.field.initializer;

import com.example.life.api.field.Field;
import com.example.life.api.field.FieldInitializer;
import com.example.life.error.GameErrorCode;
import com.example.life.error.GameOfLifeException;
import com.example.life.model.Cell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class PropertyBasedFieldInitializer implements FieldInitializer {
    private int width;
    private int height;
    private String aliveCells;

    @Override
    public void readSource(String filePath) {
        Properties props = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(filePath);
        if (in == null) {
            throw new GameOfLifeException(GameErrorCode.INVALID_FILE,
                    new FileNotFoundException("Resource not found: " + filePath));
        }
        try {
            props.load(in);
            this.width = Integer.parseInt(props.getProperty("width"));
            this.height = Integer.parseInt(props.getProperty("height"));
            this.aliveCells = props.getProperty("aliveCells", "");
        } catch (IOException e) {
            throw new GameOfLifeException(GameErrorCode.INVALID_FILE, e);
        } catch (NumberFormatException e) {
            throw new GameOfLifeException(GameErrorCode.INVALID_FORMAT, e);
        }
    }

    @Override
    public void apply(Field field) {
        if (field.getWidth() != width || field.getHeight() != height)
            throw new GameOfLifeException(GameErrorCode.INVALID_DIMENSIONS);
        if (!aliveCells.isBlank()) {
            Arrays.stream(aliveCells.split(";"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(pos -> pos.split(","))
                    .forEach(xy -> {
                        int x = Integer.parseInt(xy[0].trim());
                        int y = Integer.parseInt(xy[1].trim());
                        field.set(x, y, Cell.ALIVE);
                    });
        }
    }

    @Override
    public int getWidth() { return width; }
    @Override
    public int getHeight() { return height; }
}
