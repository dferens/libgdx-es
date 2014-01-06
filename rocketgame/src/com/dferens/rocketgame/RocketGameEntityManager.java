package com.dferens.rocketgame;

import com.dferens.core.BaseEntityManager;
import com.dferens.core.EntityPriorityResolver;
import com.dferens.core.GameConfigProvider;

public class RocketGameEntityManager extends BaseEntityManager {

    public RocketGameEntityManager(EntityPriorityResolver priorityResolver, GameConfigProvider configProvider) {
        super(priorityResolver, configProvider);
    }
}
