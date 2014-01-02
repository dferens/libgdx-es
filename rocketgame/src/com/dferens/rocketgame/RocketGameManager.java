package com.dferens.rocketgame;

import com.dferens.core.GameManager;
import com.dferens.core.UIManager;

public class RocketGameManager extends GameManager {
    public RocketGameManager(float visibleUnits) {
        super(visibleUnits);
    }

    @Override
    public UIManager createUIManager() {
        return new OnScreenUIManager();
    }
}
