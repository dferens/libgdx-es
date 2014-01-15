package com.dferens.supervasyan.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.supervasyan.Priority;

import java.util.Random;

public class BallEntity extends BlockEntity implements Renderable {
    private static final Random colorRandomizer = new Random();
    private static final float BLOCK_FRICTION = 0.95f;
    private static final float BLOCK_RESTITUTION = 0.5f;
    private static final float BLOCK_DENSITY = 0.1f;

    protected final float radius;
    protected final Color color;

    public BallEntity(float gridX, float gridY, float radius) {
        super(gridX, gridY);

        this.radius = radius;
        this.color = new Color(colorRandomizer.nextFloat(),
                               colorRandomizer.nextFloat(),
                               colorRandomizer.nextFloat(), 1);
    }

    @Override
    public PhysicsBody createBody(World world) {
        float spawnX = gridX + this.radius;
        float spawnY = gridY + this.radius;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnX, spawnY);
        Body boxBody = world.createBody(bodyDef);

        CircleShape sawShape = new CircleShape();
        sawShape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = sawShape;
        fixtureDef.friction = BLOCK_FRICTION;
        fixtureDef.restitution = BLOCK_RESTITUTION;
        fixtureDef.density = BLOCK_DENSITY;

        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        renderer.draw(ShapeRenderer.ShapeType.Filled)
                .bodyCoords(this)
                .circleInUnits(this.radius)
                .setColor(this.color)
                .commit();
    }

    @Override
    public int getRenderPriority() { return Priority.ENTITIES; }
    @Override
    public void loadAssets(AssetContainer assetContainer) { }
}
