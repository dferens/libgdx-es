package com.dferens.core;

import java.util.Iterator;

public interface IEntityManager {
    Iterator<ActiveObject> iterateEntities();
    Iterator<ActiveObject> iterateRenderables();
}
