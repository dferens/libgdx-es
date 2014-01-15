package com.dferens.libgdxes.entities;

import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;

public interface Updatable<I extends InputScope> extends Entity {
    void update(float deltaTime, Context context, I input);
    int getUpdatePriority();
}
