package com.dferens.supervasyan.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.supervasyan.Priority;

public class SawEntity extends BallEntity {
    private static final float BLOCK_FRICTION = 0.2f;
    private static final float BLOCK_RESTITUTION = 0f;
    private static final float BLOCK_DENSITY = 1f;

    private float state = 0;
    private float rotationSpeed = 5;

    public SawEntity(float gridX, float gridY, float radius) {
        super(gridX, gridY, radius);
    }

    @Override
    public PhysicsBody createBody(World world) {
        float spawnX = gridX + this.radius;
        float spawnY = gridY + this.radius;
        // TODO: optimize memory usage
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
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
        state += deltaTime;

        renderer.draw("saw", Texture.class)
                .bodyCoords(context.getBody())
                .startAt(Position.CENTER)
                .transformInUnits(2 * this.radius)
                .rotateRadians(this.state * this.rotationSpeed)
                .commit();
    }

    @Override
    public void loadAssets(AssetContainer assetContainer) {
        assetContainer.load("saw", "data/saw.png", Texture.class);
    }
    @Override
    public int getRenderPriority() { return Priority.BACKGROUND_BLOCKS_SAWS; }
}
