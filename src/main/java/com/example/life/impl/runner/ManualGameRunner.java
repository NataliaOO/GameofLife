package com.example.life.impl.runner;

import com.example.life.api.logging.FieldLogger;
import com.example.life.api.runner.GameRunner;
import com.example.life.core.GameOfLife;

import java.util.Scanner;

public class ManualGameRunner implements GameRunner {
    private final FieldLogger fieldLogger;
    private final GameOfLife gameOfLife;

    public ManualGameRunner(GameOfLife gameOfLife, FieldLogger fieldLogger) {
        this.gameOfLife = gameOfLife;
        this.fieldLogger = fieldLogger;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int generation = 0;
        while (true) {
            fieldLogger.logField(gameOfLife.getCurrentField(), generation);
            System.out.println("Press Enter for next generation or 'q' to quit:");
            String input = scanner.nextLine();
            if ("q".equalsIgnoreCase(input.trim())) break;
            gameOfLife.nextGeneration();
            generation++;
        }
    }
}
