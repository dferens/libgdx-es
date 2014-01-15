package com.dferens.supervasyan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.render.RenderScope;

public class SVInputScope extends InputScope {
    private static final float inputPadHeight = 0.2f;
    private static final Color inputPadColor = new Color(0.5f, 0.5f, 0.5f, 0.3f);

    @Override
    public void initialize() { }

    /**
     * Returns player moving rate in range [-1:+1]
     * @return negative for left side, positive for right side
     */
    public float getMovingRate() {
        double sourceValue = Gdx.input.getAccelerometerY();
        double filteredValue;
        if (Math.abs(sourceValue) < 0.5) {
            filteredValue = 0;
        } else {
            filteredValue = Math.pow(Math.abs(sourceValue), 1.3) * Math.signum(sourceValue);
        }
        return (float) (filteredValue / 20);
    }
    public boolean isJumping() {
        if (Gdx.input.isTouched()) {
            int touchYaxisUp = Gdx.graphics.getHeight() - Gdx.input.getY();
            return (touchYaxisUp < (Gdx.graphics.getHeight() * inputPadHeight));
        }
        return false;
    }

    @Override
    public void render(float deltaTime, RenderScope renderer) {
        renderer.draw(ShapeRenderer.ShapeType.Filled)
                .setColor(inputPadColor)
                .rectangleInPixels(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * inputPadHeight)
                .commit();
    }
}
