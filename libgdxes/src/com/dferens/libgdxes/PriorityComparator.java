package com.dferens.libgdxes;

import com.dferens.libgdxes.entities.Entity;

import java.util.Comparator;
import java.util.Map;

public abstract class PriorityComparator<T extends Entity> implements Comparator<T> {
    private final Map<? extends Entity, ? extends Context> lookup;

    PriorityComparator(Map<? extends Entity, ? extends Context> lookup) {
        this.lookup = lookup;
    }

    @Override
    public int compare(T o1, T o2) {
        Context c1 = lookup.get(o1);
        Context c2 = lookup.get(o2);
        int result = Integer.compare(this.getPriority(c1), this.getPriority(c2));
        if (result != 0) {
            return result;
        } else {
            return o1.equals(o2) ? 0 : 1;
        }
    }

    protected abstract int getPriority(Context c);
}
