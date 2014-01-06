package com.dferens.core;

public interface Updatable extends Entity {
    void update(float deltaTime, Context context, InputScope input);
}
