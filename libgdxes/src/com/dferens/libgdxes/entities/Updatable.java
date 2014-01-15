package com.dferens.libgdxes.entities;

import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.render.RenderScope;

public interface Updatable extends Entity {
    void update(float deltaTime, Context context, InputScope input, RenderScope renderScope);
    int getUpdatePriority();
}
