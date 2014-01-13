package com.dferens.supervasyan.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.supervasyan.Priority;
import com.dferens.supervasyan.SVInputScope;

public class VasyanEntity implements PhysicsApplied, Updatable, Renderable {
    private static final float JUMP_IMPULSE = 10f;
    private static final float MOVE_SPEED = 30f;

    private final float spawnPositionX;
    private final float spawnPositionY;
    private boolean jetpackEnabled;

    public VasyanEntity(Vector2 spawnPosition) {
        this.spawnPositionX = spawnPosition.x;
        this.spawnPositionY = spawnPosition.y;
        this.jetpackEnabled = false;
    }

    @Override
    public PhysicsBody createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPositionX, spawnPositionY);
        Body boxBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.2f;
        CircleShape rocketShape = new CircleShape();
        rocketShape.setRadius(1f);
        fixtureDef.shape = rocketShape;
        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }

    @Override
    public void update(float deltaTime, Context context, InputScope input, RenderScope renderScope) {
        SVInputScope screenInput = (SVInputScope) input;
        PhysicsBody body = context.getBody();

        float newVelocityX = (float)(MOVE_SPEED * screenInput.getMovingRate());
        float currentVelocityY = body.getLinearVelocity().y;
        body.setLinearVelocity(newVelocityX, currentVelocityY);

        if (screenInput.isJumping()) {
            this.jetpackEnabled = true;
            body.applyLinearImpulse(0, JUMP_IMPULSE, body.getX(), body.getY());
        } else {
            this.jetpackEnabled = false;
        }
    }

    @Override
    public int getUpdatePriority() { return Priority.ENTITIES; }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderScope) {
        if (this.jetpackEnabled) {
            renderScope.draw("player", ParticleEffect.class)
                       .bodyCoords(context.getBody())
                       .shiftUnits(0, 1)
                       .rotateDegrees(180)
                       .startAt(Position.CENTER)
                       .commit();
        } else {
            ParticleEffect effect = renderScope.getAssetStorage().get("player", ParticleEffect.class);
            effect.reset();
        }
        renderScope.draw("player", Texture.class)
                   .bodyCoords(context.getBody())
                   .startAt(Position.CENTER)
                   .transformInUnits(2, 2)
                   .commit();
    }

    @Override
    public int getRenderPriority() { return Priority.ENTITIES; }

    @Override
    public void loadAssets(AssetContainer assetContainer) {
        assetContainer.load("player", "data/character.png", Texture.class);
        assetContainer.load("player", "data/particles/character.p", ParticleEffect.class);
    }
}
