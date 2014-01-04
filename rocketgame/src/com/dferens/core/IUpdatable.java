package com.dferens.core;

public interface IUpdatable extends IEntity{
    void update(float deltaTime, Context context, UIManager input);
}
