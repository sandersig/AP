package com.kritjo.ap;

import java.util.ArrayList;
import java.util.Objects;

public class ProvisionPool {
    private final ArrayList<Provision> pool = new ArrayList<>();

    public void add(Provision p) {
        pool.add(p);
    }

    public int sum() {
        int sumPool = 0;
        for (Provision p : pool) {
            sumPool += p.getProvision();
        }
        return sumPool;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pool);
    }

    @Override
    public boolean equals(Object other) {
        if (!other.getClass().getSimpleName().equals(this.getClass().getSimpleName())) return false;

        return sum() == ((ProvisionPool) other).sum();
    }
}
