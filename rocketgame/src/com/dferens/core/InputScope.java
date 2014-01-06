package com.dferens.core;

import com.badlogic.gdx.Gdx;

public abstract class InputScope {
    protected Boolean isTouched() { return Gdx.input.isTouched(); }
    protected float getTouchPosX() { return Gdx.input.getX(); }
    protected float getTouchPosY() { return Gdx.input.getY(); }
    public abstract void render(float deltaTime);
}
