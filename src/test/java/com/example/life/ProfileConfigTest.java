package com.example.life;

import com.example.life.api.logging.FieldLogger;
import com.example.life.api.runner.GameRunner;
import com.example.life.core.GameOfLife;
import com.example.life.impl.runner.AutoGameRunner;
import com.example.life.impl.runner.ManualGameRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileConfigTest {

    @Test
    void manualProfileCreatesManualGameRunner() {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext()) {
            ConfigurableEnvironment env = ctx.getEnvironment();
            env.setActiveProfiles("MANUAL");
            ctx.register(com.example.life.config.GameOfLifeConfig.class);
            ctx.refresh();
            GameRunner runner = ctx.getBean(GameRunner.class);
            assertNotNull(runner);
            assertEquals(ManualGameRunner.class, runner.getClass());
        }
    }

    @Test
    void autoProfileCreatesAutoGameRunner() {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext()) {
            ConfigurableEnvironment env = ctx.getEnvironment();
            env.setActiveProfiles("AUTO");
            ctx.register(com.example.life.config.GameOfLifeConfig.class);
            ctx.refresh();
            GameRunner runner = ctx.getBean(GameRunner.class);
            assertNotNull(runner);
            assertEquals(AutoGameRunner.class, runner.getClass());
        }
    }

    @Test
    void noProfileThrowsOnGameRunnerBeanRequest() {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext()) {
            ctx.register(com.example.life.config.GameOfLifeConfig.class);
            ctx.refresh();
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(GameRunner.class));
        }
    }
}
