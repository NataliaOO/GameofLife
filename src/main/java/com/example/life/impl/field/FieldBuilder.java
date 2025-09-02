package com.example.life.impl.field;

import com.example.life.api.field.Field;
import com.example.life.api.field.FieldInitializer;

public class FieldBuilder {
    private FieldFactory factory;
    private String filePath;
    private FieldInitializer initializer;

    public FieldBuilder source(String s) {
        this.filePath = s;
        return this;
    }

    public FieldBuilder withInitializer(FieldInitializer initializer) {
        this.initializer = initializer;
        return this;
    }

    public FieldBuilder factory(FieldFactory factory) {
        this.factory = factory;
        return this;
    }

    public Field build() {
        if (initializer == null || filePath == null || factory == null)
            throw new IllegalStateException("Initializer, file path and factory must be set");

        initializer.readSource(filePath);
        Field field = factory.create(initializer.getWidth(), initializer.getHeight());
        initializer.apply(field);
        return field;
    }
}
