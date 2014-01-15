package com.dferens.libgdxes;

import com.dferens.libgdxes.render.AssetStorage;

public class Context{
    private final int updatePriority;
    private final int renderPriority;
    private final EntityManager entityManager;
    private final String[] assets;
    private PhysicsBody[] bodies;

    public int getUpdatePriority() { return this.updatePriority; }
    public int getRenderPriority() { return this.renderPriority; }
    public EntityManager getEntityManager() { return this.entityManager; }
    public String[] getAssets() { return this.assets; }

    public Context(EntityManager entityManager,
                   Integer updatePriority, Integer renderPriority,
                   PhysicsBody[] bodies,
                   String[] assets) {
        if (updatePriority == null) updatePriority = -1;
        if (renderPriority == null) renderPriority = -1;

        this.entityManager = entityManager;
        this.updatePriority = updatePriority;
        this.renderPriority = renderPriority;
        this.assets = (assets != null) ? assets : new String[0];
        this.bodies = (bodies != null) ? bodies : new PhysicsBody[0];
    }
    public PhysicsBody[] getBodies() { return this.bodies; }
    public PhysicsBody getBody(String alias) {
        for (PhysicsBody body : bodies) {
            String bodyAlias = body.getAlias();
            if ((bodyAlias != null) && bodyAlias.equals(alias)) {
                return body;
            }
        }
        return null;
    }
    public PhysicsBody getBody() {
        return (this.bodies == null) ? null : this.bodies[0];
    }
    public void destroyBodies() {
        if (this.bodies != null) {
            for (PhysicsBody body : this.bodies) {
                body.destroy();
            }
            this.bodies = null;
        }
    }
    public void unloadAssets(AssetStorage assetStorage) {
        if (this.assets != null) {
            for (String asset : this.assets) {
                assetStorage.unloadAsset(asset);
            }
        }
    }
}
