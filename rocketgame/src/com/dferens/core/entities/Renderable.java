package com.dferens.core.entities;

import com.dferens.core.Context;
import com.dferens.core.RenderScope;

public interface Renderable extends Entity {
    void render(float deltaTime, Context context, RenderScope renderer);
    int getRenderPriority();
}
