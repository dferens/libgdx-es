package com.dferens.libgdxes;

import com.dferens.libgdxes.render.RenderScope;

public abstract class InputScope implements Scope {
    public abstract void render(float deltaTime, RenderScope renderer);
}
