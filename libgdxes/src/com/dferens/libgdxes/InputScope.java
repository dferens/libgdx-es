package com.dferens.libgdxes;

import com.badlogic.gdx.Gdx;

public abstract class InputScope implements Scope {
    protected Boolean isTouched() { return Gdx.input.isTouched(); }
    protected float getTouchPosX() { return Gdx.input.getX(); }
    protected float getTouchPosY() { return Gdx.input.getY(); }
    public abstract void render(float deltaTime);
}
