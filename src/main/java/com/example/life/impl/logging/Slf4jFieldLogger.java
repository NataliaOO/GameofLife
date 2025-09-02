package com.example.life.impl.logging;

import com.example.life.api.field.Field;
import com.example.life.api.logging.FieldLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Slf4jFieldLogger implements FieldLogger {
    private static final Logger log = LoggerFactory.getLogger(Slf4jFieldLogger.class);

    @Override
    public void logField(Field field, int generation) {
        String grid = IntStream.range(0, field.getHeight())
                .mapToObj(y ->
                        IntStream.range(0, field.getWidth())
                                .mapToObj(x -> field.get(x, y).isAlive() ? "O " : ". ")
                                .collect(Collectors.joining())
                )
                .collect(Collectors.joining("\n"));

        log.info("\nGeneration: {}\n{}", generation, grid);
    }
}
