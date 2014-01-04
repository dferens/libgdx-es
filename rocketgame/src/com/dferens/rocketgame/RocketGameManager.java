package com.dferens.rocketgame;

import com.dferens.core.*;

public class RocketGameManager extends GameManager {

    public RocketGameManager(GameConfig config) {
        super(config);
    }

    @Override
    public UIManager createUIManager() {
        return new OnScreenUIManager();
    }

    @Override
    public IEntityManager createEntityManager(IGameConfigProvider configProvider) {
        return new RocketGameEntityManager(new RocketGameEntityPriorityResolver(), this);
    }
}
