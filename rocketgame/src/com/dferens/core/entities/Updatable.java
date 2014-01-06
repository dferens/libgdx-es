package com.dferens.core.entities;

import com.dferens.core.Context;
import com.dferens.core.InputScope;

public interface Updatable extends Entity {
    void update(float deltaTime, Context context, InputScope input);
    int getUpdatePriority();
}
