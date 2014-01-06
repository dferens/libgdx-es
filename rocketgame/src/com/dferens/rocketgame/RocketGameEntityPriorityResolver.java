package com.dferens.rocketgame;

import com.dferens.core.IEntityPriorityResolver;
import com.dferens.core.IRenderable;
import com.dferens.core.IUpdatable;

public class RocketGameEntityPriorityResolver implements IEntityPriorityResolver {
    @Override
    public int getRenderPriority(IRenderable entity) {
        return 0;
    }

    @Override
    public int getUpdatePriority(IUpdatable entity) {
        return 0;
    }
}
