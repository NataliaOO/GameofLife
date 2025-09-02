package com.example.life.impl.runner;

import com.example.life.api.logging.FieldLogger;
import com.example.life.api.runner.GameRunner;
import com.example.life.core.GameOfLife;
import com.example.life.impl.logging.Slf4jFieldLogger;

public class AutoGameRunner implements GameRunner {
    private final FieldLogger fieldLogger;
    private final GameOfLife gameOfLife;

    private volatile boolean running = true;

    public void stop() {
        running = false;
    }

    public AutoGameRunner(GameOfLife gameOfLife, FieldLogger fieldLogger) {
        this.gameOfLife = gameOfLife;
        this.fieldLogger = fieldLogger;
    }

    @Override
    public void run() {
        int generation = 0;
        while (running) {
            fieldLogger.logField(gameOfLife.getCurrentField(), generation);
            gameOfLife.nextGeneration();
            generation++;
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
