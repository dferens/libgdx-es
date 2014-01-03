package com.dferens.core;

public interface IRenderable extends IEntity {
    void render(float deltaTime, GameContext context, GameRenderer renderer);
}
