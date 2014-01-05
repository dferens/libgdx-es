package com.dferens.rocketgame;

import com.dferens.core.EntityManager;
import com.dferens.core.IEntityPriorityResolver;
import com.dferens.core.IGameConfigProvider;

public class RocketGameEntityManager extends EntityManager {

    public RocketGameEntityManager(IEntityPriorityResolver priorityResolver, IGameConfigProvider configProvider) {
        super(priorityResolver, configProvider, null);
    }
}
