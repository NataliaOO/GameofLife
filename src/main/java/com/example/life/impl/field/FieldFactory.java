package com.example.life.impl.field;

import com.example.life.api.field.Field;

public interface FieldFactory {
    Field create(int width, int height);
}
