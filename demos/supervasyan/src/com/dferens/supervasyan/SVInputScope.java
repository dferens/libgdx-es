package com.dferens.supervasyan;

import com.badlogic.gdx.Gdx;
import com.dferens.libgdxes.InputScope;

public class SVInputScope extends InputScope {
    @Override
    public void initialize() { }

    /**
     * Returns player moving rate in range [-30; 30]
     * @return negative negative for left side, positive for right side
     */
    public double getMovingRate() {
        double sourceValue = Gdx.input.getAccelerometerY();
        double filteredValue;
        if (Math.abs(sourceValue) < 1) {
            filteredValue = 0;
        } else {
            filteredValue = Math.pow(Math.abs(sourceValue), 1.477) * Math.signum(sourceValue);
        }
        return filteredValue / 10;
    }
    public boolean isJumping() {
        if (Gdx.input.isTouched()) {
            return ((Gdx.graphics.getHeight() - Gdx.input.getY()) < (Gdx.graphics.getHeight() / 4));
        }
        return false;
    }

    @Override
    public void render(float deltaTime) {
        // No GUI
    }
}
