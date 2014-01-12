package com.dferens.rocketgame.entities;

import com.badlogic.gdx.math.Vector2;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.utils.AxisTracker;
import com.dferens.rocketgame.Priority;

public class CameramanEntity implements Updatable {
    protected PhysicsApplied entity;
    protected AxisTracker xAxisTracker;
    protected AxisTracker yAxisTracker;

    public CameramanEntity() {
        this.xAxisTracker = new AxisTracker(0.9f, 0.1f);
        this.yAxisTracker = new AxisTracker(Float.MAX_VALUE, Float.MIN_VALUE);
    }

    public void setEntityToTrack(PhysicsApplied entity) {
        // TODO: change update priority
        this.entity = entity;
    }
    public void setAxisX(float forwardCoeff, float backwardCoeff) {
        this.xAxisTracker.setForward(forwardCoeff);
        this.xAxisTracker.setBackward(backwardCoeff);
    }
    public void setAxisY(float forwardCoeff, float backwardCoeff) {
        this.yAxisTracker.setForward(forwardCoeff);
        this.yAxisTracker.setBackward(backwardCoeff);
    }

    public void moveCamera(RenderScope renderScope, Vector2 bodyPosition) {
        Vector2 viewportPosition = renderScope.getViewportCoords(bodyPosition);
        float dx = this.xAxisTracker.calculateCameraTranslate(viewportPosition.x);
        float dy = this.yAxisTracker.calculateCameraTranslate(viewportPosition.y);
        renderScope.moveCameraByViewport(dx, dy);
    }

    @Override
    public void update(float deltaTime, Context context, InputScope input, RenderScope renderScope) {
        if (this.entity != null) {
            PhysicsBody body = context.getEntityManager().getContext(this.entity).getBody();
            Vector2 trackedEntityPosition = body.getPosition();
            this.moveCamera(renderScope, trackedEntityPosition);
        }
    }

    @Override
    public int getUpdatePriority() { return Priority.FOREGROUND; }
}
