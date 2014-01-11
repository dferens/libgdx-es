package com.dferens.libgdxes.tests;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.entities.Entity;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;

public class TestEntityFactory {
    public class TestEntity extends Object implements Entity {
        public int renderTimesCalled = 0;
        public int updateTimesCalled = 0;
        public int createBodyTimesCalled = 0;
        public int getUpdatePriorityTimesCalled = 0;
        public int getRenderPriorityTimesCalled = 0;
    }
    class RenderableTestEntity extends TestEntity implements Renderable {
        private int priority;
        RenderableTestEntity(int priority) { this.priority = priority; }
        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) { renderTimesCalled += 1; }
        @Override
        public int getRenderPriority() { getRenderPriorityTimesCalled += 1; return priority; }
    };
    class UpdatableTestEntity extends TestEntity implements Updatable {
        private int priority;
        UpdatableTestEntity(int priority) { this.priority = priority; }
        @Override
        public void update(float deltaTime, Context context, InputScope input) { updateTimesCalled += 1; }
        @Override
        public int getUpdatePriority() { getUpdatePriorityTimesCalled += 1; return priority; }
    };
    class PhysicsAppliedTestEntity extends TestEntity implements PhysicsApplied {
        @Override
        public PhysicsBody createBody(World world) {
            createBodyTimesCalled += 1;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            return new PhysicsBody(world.createBody(bodyDef));
        }
    };

    public TestEntity createSimpleEntity() {
        return new TestEntity() { };
    }
    public RenderableTestEntity createRenderableEntity(int priority) {
        return new RenderableTestEntity(priority);
    }
    public UpdatableTestEntity createUpdatableEntity(int priority) {
        return new UpdatableTestEntity(priority);
    }
    public PhysicsAppliedTestEntity createPhysicsApplied() {
        return new PhysicsAppliedTestEntity();
    }
    public RenderableTestEntity createRenderableEntity() { return createRenderableEntity(0); }
    public UpdatableTestEntity createUpdatableEntity() { return createUpdatableEntity(0); }
}
