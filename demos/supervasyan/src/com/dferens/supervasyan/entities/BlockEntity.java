package com.dferens.supervasyan.entities;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.PhysicsBody;

/**
 * As blocks should be rendered with specific map mapRenderer, block has no render method
 * @see OrthogonalTiledMapRenderer;
 */
public class BlockEntity implements PhysicsApplied {
    private static final float BLOCK_FRICTION = 0f;
    private static final float BLOCK_RESTITUTION = 0f;
    private static final float BLOCK_DENSITY = 1f;

    protected final float gridX;
    protected final float gridY;

    public BlockEntity(float gridX, float gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    @Override
    public PhysicsBody createBody(World world) {
        // TODO: optimize memory usage
        float spawnX = gridX + 0.5f;
        float spawnY = gridY + 0.5f;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(spawnX, spawnY);
        Body boxBody = world.createBody(bodyDef);

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(0.5f, 0.5f, Vector2.Zero, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.friction = BLOCK_FRICTION;
        fixtureDef.restitution = BLOCK_RESTITUTION;
        fixtureDef.density = BLOCK_DENSITY;

        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }
}
