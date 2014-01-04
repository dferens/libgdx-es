package com.dferens.rocketgame;

import com.dferens.core.IEntity;
import com.dferens.core.IEntityPriorityResolver;

public class RocketGameEntityPriorityResolver implements IEntityPriorityResolver {
    @Override
    public int getRenderPriority(IEntity entity) {
        return 0;
    }

    @Override
    public int getUpdatePriority(IEntity entity) {
        return 0;
    }
}
