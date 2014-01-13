package com.dferens.libgdxes.render.drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dferens.libgdxes.render.Position;

public class ParticleEffectDrawable extends Drawable {
    private ParticleEffect particleEffect;

    public ParticleEffectDrawable(ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
        this.particleEffect.setPosition(0, 0);
        this.particleEffect.start();

    }

    @Override
    public void execute(SpriteBatch batch, float deltaTime,
                        float x, float y, Position position,
                        float width, float height, float scaleX, float scaleY,
                        float angle) {
        batch.end();
        batch.begin();
        y = Gdx.graphics.getHeight() - y;
        this.particleEffect.setPosition(x, y);
        this.particleEffect.draw(batch, deltaTime);

    }
}
