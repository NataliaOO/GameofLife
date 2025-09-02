package com.example.life.api.field;

public interface FieldInitializer {
    void readSource(String filePath);
    void apply(Field field);
    int getWidth();
    int getHeight();
}
