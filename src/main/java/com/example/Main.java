package com.example;

import com.example.life.api.runner.GameRunner;
import com.example.life.config.GameOfLifeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(GameOfLifeConfig.class)) {
            ctx.getBean(GameRunner.class).run();
        }
    }
}