package com.dferens.libgdxes.render.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dferens.libgdxes.render.DrawChain;
import com.dferens.libgdxes.render.RenderScope;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ShapeDrawChain extends DrawChain<ShapeRenderer, ShapeDrawChain> {
    public enum ShapeFigure {
        CIRCLE,
        ELLIPSE,
        RECTANGLE,
    }

    private ShapeType shapeType;
    private ShapeFigure shapeFigure;
    private Color color;
    private float x1 = 0, x2 = 0, x3 = 0 ,x4 = 0, x5 = 0;

    public ShapeDrawChain(RenderScope renderScope, ShapeType shapeType) {
        super(renderScope);
        this.shapeType = shapeType;
        this.shapeFigure = null;
        this.color = new Color(Color.BLACK);
    }

    public ShapeDrawChain setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        return this;
    }
    public ShapeDrawChain setColor(Color color) {
        return this.setColor(color.r, color.g, color.b, color.a);
    }

    public ShapeDrawChain circleInUnits(float radius) {
        this.shapeFigure = ShapeFigure.CIRCLE;
        this.x1 = this.renderScope.unitsToPixels(radius);
        return this;
    }
    public ShapeDrawChain ellipseInUnits(float width, float height) {
        this.shapeFigure = ShapeFigure.ELLIPSE;
        this.x1 = this.renderScope.unitsToPixels(width);
        this.x2 = this.renderScope.unitsToPixels(height);
        return this;
    }
    public ShapeDrawChain rectangleInPixels(float width, float height) {
        this.shapeFigure = ShapeFigure.RECTANGLE;
        this.x1 = width;
        this.x2 = height;
        return this;
    }
    public ShapeDrawChain rectangleInUnits(float width, float height) {
        return this.rectangleInPixels(renderScope.unitsToPixels(width), renderScope.unitsToPixels(height));
    }
    public ShapeDrawChain rectangleInUnits(float widthHeight) {
        return this.rectangleInUnits(widthHeight, widthHeight);
    }

    @Override
    public void execute(ShapeRenderer renderObject, float deltaTime) {
        this.renderScope.commitDraw(false);

        if (this.shapeFigure != null) {
            renderObject.begin(this.shapeType);
            renderObject.setColor(this.color);
            if (this.shapeFigure == ShapeFigure.CIRCLE) {
                renderObject.circle(this.posPixelsX, this.posPixelsY, x1);
            } else if (this.shapeFigure == ShapeFigure.ELLIPSE) {
                renderObject.ellipse(this.posPixelsX, this.posPixelsY, x1, x2);
            } else if (this.shapeFigure == ShapeFigure.RECTANGLE) {
                float originX = this.posPixelsX + x1 / 2;
                float originY = this.posPixelsY + x2 / 2;
                renderObject.rect(this.posPixelsX, this.posPixelsY, x1, x2, originX, originY, this.rotationAngleDegrees);
            }
            // TODO: implement other shapes
            renderObject.end();
        }

        this.renderScope.beginDraw();
    }
}
