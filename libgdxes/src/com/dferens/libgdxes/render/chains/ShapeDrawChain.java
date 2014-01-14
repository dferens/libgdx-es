package com.dferens.libgdxes.render.chains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.render.DrawChain;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.drawable.Drawable;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ShapeDrawChain extends DrawChain<ShapeRenderer> {
    public enum ShapeFigure {
        CIRCLE,
        ELLIPSE;
    }

    private float posPixelsX;
    private float posPixelsY;
    private ShapeType shapeType;
    private ShapeFigure shapeFigure;
    private Color color;
    private float x1 = 0, x2 = 0, x3 = 0 ,x4 = 0, x5 = 0;
    private Vector3 temp;

    public ShapeDrawChain(RenderScope renderScope, Drawable drawable) {
        super(renderScope, drawable);

        this.posPixelsX = 0;
        this.posPixelsY = 0;
        this.shapeType = ShapeType.Line;
        this.shapeFigure = null;
        this.color = new Color();
        this.temp = new Vector3();
    }

    public ShapeDrawChain screenCoords(float x, float y) {
        this.posPixelsX = x;
        this.posPixelsY = y;
        return this;
    }

    public ShapeDrawChain screenCoords(Vector2 coords) {
        return this.screenCoords(coords.x, coords.y);
    }

    public ShapeDrawChain worldCoords(float x, float y) {
        this.temp.set(x, y, 0);
        this.renderScope.unitsToPixels(this.temp);
        return this.screenCoords(this.temp.x, this.temp.y);
    }

    public ShapeDrawChain worldCoords(Vector2 coords) {
        return worldCoords(coords.x, coords.y);
    }

    public ShapeDrawChain bodyCoords(PhysicsBody body) {
        return this.worldCoords(body.getX(), body.getY());
    }

    public ShapeDrawChain shapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
        return this;
    }
    public ShapeDrawChain shapeFigure(ShapeFigure figure) {
        this.shapeFigure = figure;
        return this;
    }
    public ShapeDrawChain setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        return this;
    }
    public ShapeDrawChain setColor(Color color) {
        return this.setColor(color.r, color.g, color.b, color.a);
    }

    public ShapeDrawChain setRadiusUnits(float radius) {
        this.x1 = this.renderScope.unitsToPixels(radius);
        return this;
    }

    @Override
    public void execute(ShapeRenderer renderObject, float deltaTime) {
        this.renderScope.commitDraw(false);

        if (this.shapeFigure != null) {
            renderObject.begin(this.shapeType);
            renderObject.setColor(this.color);
            if (this.shapeFigure == ShapeFigure.CIRCLE) {
                renderObject.circle(this.posPixelsX, this.posPixelsY, x1);
            }
            // TODO: implement other shapes
            renderObject.end();
        }

        this.renderScope.beginDraw();
    }
}
