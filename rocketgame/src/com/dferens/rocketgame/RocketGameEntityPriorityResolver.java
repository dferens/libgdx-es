package com.dferens.rocketgame;

import com.dferens.core.EntityPriorityResolver;
import com.dferens.core.Renderable;
import com.dferens.core.Updatable;

public class RocketGameEntityPriorityResolver implements EntityPriorityResolver {
    @Override
    public int getRenderPriority(Renderable entity) {
        return 0;
    }

    @Override
    public int getUpdatePriority(Updatable entity) {
        return 0;
    }
}
