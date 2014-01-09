package com.dferens.libgdxes.entities;

import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;

public interface Updatable extends Entity {
    void update(float deltaTime, Context context, InputScope input);
    int getUpdatePriority();
}
