package com.example.life;

import com.example.life.api.field.Field;
import com.example.life.api.field.FieldInitializer;
import com.example.life.api.rules.Rules;
import com.example.life.core.Create;
import com.example.life.core.GameOfLife;
import com.example.life.error.GameErrorCode;
import com.example.life.error.GameOfLifeException;
import com.example.life.impl.field.BoundedField;
import com.example.life.impl.field.initializer.PropertyBasedFieldInitializer;
import com.example.life.impl.field.initializer.TextMatrixFieldInitializer;
import com.example.life.impl.rules.StandardRules;
import com.example.life.model.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GameofLifeTest {

    @Test
    void testFieldInitializationFromProperties() {
        Field field = Create.field()
                .withInitializer(new PropertyBasedFieldInitializer())
                .source("first-generation.properties")
                .factory(BoundedField::new)
                .build();

        assertNotNull(field, "Field must not be null");
    }

    @Test
    void testAliveCellsAreInitializedCorrectly() {
        Field field = Create.field()
                .withInitializer(new PropertyBasedFieldInitializer())
                .source("first-generation.properties")
                .factory(BoundedField::new)
                .build();

        assertEquals(5, field.getWidth());
        assertEquals(5, field.getHeight());
        assertTrue(field.get(0, 0).isAlive());
        assertTrue(field.get(1, 1).isAlive());
        assertTrue(field.get(2, 2).isAlive());
    }

    @Test
    void testFieldInitializationFromTextMatrix() {
        Field field = Create.field()
                .withInitializer(new TextMatrixFieldInitializer())
                .source("second-generation.properties")
                .factory(BoundedField::new)
                .build();

        assertEquals(5, field.getWidth());
        assertEquals(5, field.getHeight());
        assertTrue(field.get(2, 1).isAlive());
        assertTrue(field.get(2, 2).isAlive());
        assertTrue(field.get(2, 3).isAlive());
    }

    @Test
    void testNextGenerationBlinker() {
        Field field = Create.field()
                .withInitializer(new TextMatrixFieldInitializer())
                .source("second-generation.properties")
                .factory(BoundedField::new)
                .build();

        GameOfLife game = new GameOfLife(field, new StandardRules());
        game.nextGeneration();
        Field next = game.getCurrentField();

        assertTrue(next.get(1, 2).isAlive());
        assertTrue(next.get(2, 2).isAlive());
        assertTrue(next.get(3, 2).isAlive());

        for (int y = 0; y < next.getHeight(); y++) {
            for (int x = 0; x < next.getWidth(); x++) {
                if (!(x == 1 && y == 2) && !(x == 2 && y == 2) && !(x == 3 && y == 2)) {
                    assertFalse(next.get(x, y).isAlive(), "Expected (" + x + "," + y + ") dead");
                }
            }
        }
    }

    @Test
    void testInvalidFileThrowsException() {
        FieldInitializer initializer = new PropertyBasedFieldInitializer();
        GameOfLifeException ex = assertThrows(
                GameOfLifeException.class,
                () -> initializer.readSource("not-exists-file.properties")
        );
        assertEquals(GameErrorCode.INVALID_FILE, ex.getErrorCode());
    }

}
