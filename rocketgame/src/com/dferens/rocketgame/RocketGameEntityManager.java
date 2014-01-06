package com.dferens.rocketgame;

import com.dferens.core.EntityManager;
import com.dferens.core.EntityPriorityResolver;
import com.dferens.core.GameConfigProvider;

public class RocketGameEntityManager extends EntityManager {

    public RocketGameEntityManager(EntityPriorityResolver priorityResolver, GameConfigProvider configProvider) {
        super(priorityResolver, configProvider);
    }
}
