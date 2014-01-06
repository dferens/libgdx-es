package com.dferens.core;

public interface Renderable extends Entity {
    void render(float deltaTime, Context context, RenderScope renderer);
}
