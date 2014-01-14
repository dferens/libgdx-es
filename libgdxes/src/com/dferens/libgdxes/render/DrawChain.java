package com.dferens.libgdxes.render;

import com.dferens.libgdxes.render.drawable.Drawable;

public abstract class DrawChain<T> {
    protected final RenderScope renderScope;
    protected final Drawable drawable;


    public DrawChain(RenderScope renderScope, Drawable drawable) {
        this.renderScope = renderScope;
        this.drawable = drawable;
    }

    public void commit() { this.renderScope.render(this); }

    public abstract void execute(T renderObject, float deltaTime);
}
