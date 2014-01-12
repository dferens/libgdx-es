package com.dferens.libgdxes.entities.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.utils.StandardPriorities;

public class CameramanEntity implements Updatable {

    protected PhysicsApplied entity;
    protected Rectangle boundingBox;
    protected int updatePriority;
    protected boolean initialized;

    public void setEntity(PhysicsApplied entity) {
        this.entity = entity;

        if (entity instanceof Updatable) {
            this.updatePriority = ((Updatable) entity).getUpdatePriority();
        } else {
            this.updatePriority = StandardPriorities.FIRST;
        }
    }

    public CameramanEntity(PhysicsApplied toTrack) {
        this.boundingBox = new Rectangle(0, 0, 5, 5).setCenter(0, 0);
        this.initialized = false;
        setEntity(toTrack);
    }
    public CameramanEntity() { this(null); }

    private Vector2 getCameraTranslate(float bodyX, float bodyY) {
        float left = this.boundingBox.getX();
        float right = this.boundingBox.getX() + this.boundingBox.getWidth();
        float bottom = this.boundingBox.getY();
        float top = this.boundingBox.getY() + this.boundingBox.getHeight();
        float resultX = 0, resultY = 0;

        if (bodyX < left) {
            resultX = (bodyX - left);
        } else if (bodyX > right) {
            resultX = (bodyX - right);
        }
        if (bodyY < bottom) {
            resultY = (bodyY - bottom);
        } else if (bodyY > top) {
            resultY = (bodyY - top);
        }

        if ((resultX != 0) || (resultY != 0)) {
            return new Vector2(resultX, resultY);
        } else {
            return null;
        }
    }

    @Override
    public void update(float deltaTime, Context context, InputScope input, RenderScope renderScope) {
        if (this.entity != null) {
            Vector2 bodyPos = context.getEntityManager()
                                     .getContext(this.entity)
                                     .getBody()
                                     .getPosition();
            if (this.initialized) {
                Vector2 cameraTranslate = this.getCameraTranslate(bodyPos.x, bodyPos.y);
                if (cameraTranslate != null) {
                    renderScope.moveCameraBy(cameraTranslate.x, cameraTranslate.y);
                    cameraTranslate.add(this.boundingBox.x, this.boundingBox.y);
                    this.boundingBox.setPosition(cameraTranslate);
                }
            } else {
                this.boundingBox.setCenter(bodyPos);
                renderScope.moveCameraTo(bodyPos);
                this.initialized = true;
            }
        }
    }

    @Override
    public int getUpdatePriority() { return this.updatePriority; }
}