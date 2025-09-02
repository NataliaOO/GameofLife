package com.example.life;

import com.example.life.api.field.Field;
import com.example.life.api.field.FieldInitializer;
import com.example.life.core.GameOfLife;
import com.example.life.error.GameErrorCode;
import com.example.life.error.GameOfLifeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.example.life.config.GameOfLifeConfig.class)
public class GameOfLifeSpringTest {

    @Autowired
    private Field field;

    @Autowired
    private GameOfLife gameOfLife;

    @Autowired
    private FieldInitializer propertyBasedFieldInitializer;

    @Test
    void testFieldBeanLoaded() {
        assertNotNull(field, "Field must not be null");
        assertEquals(5, field.getWidth());
        assertEquals(5, field.getHeight());
    }

    @Test
    void testAliveCellsInitializedFromProperties() {
        assertTrue(field.get(0, 0).isAlive());
        assertTrue(field.get(1, 1).isAlive());
        assertTrue(field.get(2, 2).isAlive());
    }

    @Test
    void testGameOfLifeBeanWorks() {
        assertNotNull(gameOfLife);
        gameOfLife.nextGeneration();
        assertNotNull(gameOfLife.getCurrentField());
    }

    @Test
    void testInvalidFileThrowsException() {
        GameOfLifeException ex = assertThrows(
                GameOfLifeException.class,
                () -> propertyBasedFieldInitializer.readSource("not-exists-file.properties")
        );
        assertEquals(GameErrorCode.INVALID_FILE, ex.getErrorCode());
    }
}
