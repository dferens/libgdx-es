package com.dferens.core;

import com.dferens.core.entities.Entity;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class EntityManagerTest extends LibgdxTest implements SettingsProvider {
    private class TestEntityManager extends EntityManager {

        protected TestEntityManager(SettingsProvider settingsProvider, GameWorld world) {
            super(settingsProvider, world);
        }
    }
    private EntityManager entityManager;

    private Settings gameSettings;
    private TestEntityFactory entityFactory;
    private List<TestEntityFactory.TestEntity> testEntities;
    private TestEntityFactory.TestEntity simpleEntity;
    private TestEntityFactory.UpdatableTestEntity updatableEntity;
    private TestEntityFactory.RenderableTestEntity renderableEntity;
    private TestEntityFactory.PhysicsAppliedTestEntity physicsAppliedEntity;
    private int updatableEntityPriority = 0;
    private int renderableEntityPriority = 1;

    private void createEntity(Collection<? extends Entity> entities) {
        for (Entity e : entities) entityManager.createEntity(e);
    }
    private void createEntity(Entity entity) { entityManager.createEntity(entity); }
    private void destroyEntity(Entity entity) { entityManager.destroyEntity(entity); }
    private Collection<Updatable> iterateUpdatables() { return entityManager.iterateUpdatables(); }
    private Collection<Renderable> iterateRenderables() { return entityManager.iterateRenderables(); }
    private Context getContext(Entity entity) { return entityManager.getContext(entity); }
    private int getNumberOfEntities() { return entityManager.getNumberOfEntities(); }

    @Override
    public Settings getSettings() { return gameSettings; }

    @Before
    public void setUp() throws Exception{
        gameSettings = new Settings();
        entityManager = new TestEntityManager(this, new GameWorld(this));
        entityFactory = new TestEntityFactory();
        testEntities = new LinkedList<TestEntityFactory.TestEntity>();
        simpleEntity = entityFactory.createSimpleEntity();
        updatableEntity = entityFactory.createUpdatableEntity(updatableEntityPriority);
        renderableEntity = entityFactory.createRenderableEntity(renderableEntityPriority);
        physicsAppliedEntity = entityFactory.createPhysicsApplied();
        testEntities.add(simpleEntity);
        testEntities.add(updatableEntity);
        testEntities.add(renderableEntity);
        testEntities.add(physicsAppliedEntity);
    }

    @Test
    public void testGetSettings() throws Exception {
        assertEquals(entityManager.getSettings(), this.gameSettings);
    }

    @Test
    public void testCreateEntity() throws Exception {
        createEntity(testEntities);
        Context simpleEntityContext = getContext(simpleEntity);
        Context updatableEntityContext = getContext(updatableEntity);
        Context renderableEntityContext = getContext(renderableEntity);
        Context physicsAppliedEntityContext = getContext(physicsAppliedEntity);
        List<Context> testEntitiesContexts = Arrays.asList(simpleEntityContext, updatableEntityContext,
                                                           renderableEntityContext, physicsAppliedEntityContext);

        assertEquals(iterateUpdatables().size(), 1);
        assertEquals(iterateRenderables().size(), 1);

        assertThat(iterateUpdatables(), hasItem((Updatable) updatableEntity));
        assertThat(iterateRenderables(), hasItem((Renderable)renderableEntity));

        assertEquals(updatableEntity.getUpdatePriorityTimesCalled, 1);
        assertEquals(renderableEntity.getRenderPriorityTimesCalled, 1);
        assertEquals(physicsAppliedEntity.createBodyTimesCalled, 1);

        assertNull(simpleEntityContext.getBody());
        assertNull(updatableEntityContext.getBody());
        assertNull(renderableEntityContext.getBody());
        assertNotNull(physicsAppliedEntityContext.getBody());

        for (Context c : testEntitiesContexts) {
            assertEquals(c.getEntityManager(), entityManager);
        }
    }

    @Test
    public void testDestroyEntity() throws Exception {
        createEntity(simpleEntity);
        createEntity(updatableEntity);
        createEntity(renderableEntity);
        createEntity(physicsAppliedEntity);
        int entitiesNumber = getNumberOfEntities();

        destroyEntity(updatableEntity);
        assertEquals(iterateUpdatables().size(), 0);
        assertEquals(getNumberOfEntities(), entitiesNumber - 1);

        destroyEntity(renderableEntity);
        assertEquals(iterateRenderables().size(), 0);
        assertEquals(getNumberOfEntities(), entitiesNumber - 2);

        Context physicsAppliedEntityContext = getContext(physicsAppliedEntity);
        destroyEntity(physicsAppliedEntity);
        assertNull(physicsAppliedEntityContext.getBody());
        assertEquals(getNumberOfEntities(), entitiesNumber - 3);

        destroyEntity(simpleEntity);
        assertEquals(getNumberOfEntities(), entitiesNumber - 4);
    }

    @Test
    public void testClear() throws Exception {
        createEntity(simpleEntity);
        createEntity(updatableEntity);
        createEntity(renderableEntity);
        createEntity(physicsAppliedEntity);

        entityManager.clear();

        assertEquals(getNumberOfEntities(), 0);
        assertEquals(iterateUpdatables().size(), 0);
        assertEquals(iterateRenderables().size(), 0);
    }

    @Test
    public void testIterateUpdatables() throws Exception {
        // TODO: test order
    }

    @Test
    public void testIterateRenderables() throws Exception {
        // TODO: test order
    }

    @Test
    public void testGetContext() throws Exception {
        createEntity(simpleEntity);
        createEntity(updatableEntity);
        createEntity(renderableEntity);
        createEntity(physicsAppliedEntity);
        Context simpleEntityContext = getContext(simpleEntity);
        Context updatableEntityContext = getContext(updatableEntity);
        Context renderableEntityContext = getContext(renderableEntity);
        Context physicsAppliedEntityContext = getContext(physicsAppliedEntity);

        assertNotNull(simpleEntityContext);
        assertNotNull(updatableEntityContext);
        assertNotNull(renderableEntityContext);
        assertNotNull(physicsAppliedEntityContext);
    }
}
