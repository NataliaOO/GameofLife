package com.example.life.config;

import com.example.life.api.field.Field;
import com.example.life.api.field.FieldInitializer;
import com.example.life.api.logging.FieldLogger;
import com.example.life.api.rules.Rules;
import com.example.life.api.runner.GameRunner;
import com.example.life.core.GameOfLife;
import com.example.life.impl.field.BoundedField;
import com.example.life.impl.field.initializer.PropertyBasedFieldInitializer;
import com.example.life.impl.field.initializer.TextMatrixFieldInitializer;
import com.example.life.impl.logging.Slf4jFieldLogger;
import com.example.life.impl.rules.StandardRules;
import com.example.life.impl.runner.AutoGameRunner;
import com.example.life.impl.runner.ManualGameRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GameOfLifeConfig {

    @Value("${init.filepath:first-generation.properties}")
    private String filePath;

    @Bean
    public Field field(FieldInitializer fieldInitializer) {
        Field field = new BoundedField(fieldInitializer.getWidth(), fieldInitializer.getHeight());
        fieldInitializer.apply(field);
        return field;
    }

    @Bean
    public FieldInitializer fieldInitializer() {
        PropertyBasedFieldInitializer initializer = new PropertyBasedFieldInitializer();
        initializer.readSource(filePath);
        return initializer;
    }

    @Bean
    public Rules rules() {
        return new StandardRules();
    }

    @Bean
    public FieldLogger fieldLogger() {
        return new Slf4jFieldLogger();
    }

    @Bean
    @Profile("MANUAL")
    public GameRunner manualGameRunner(GameOfLife gameOfLife, FieldLogger fieldLogger) {
        return new ManualGameRunner(gameOfLife, fieldLogger);
    }

    @Bean
    @Profile("AUTO")
    public GameRunner autoGameRunner(GameOfLife gameOfLife, FieldLogger fieldLogger) {
        return new AutoGameRunner(gameOfLife, fieldLogger);
    }


    @Bean
    public GameOfLife gameOfLife(Field field, Rules rules) {
        return new GameOfLife(field, rules);
    }
}
