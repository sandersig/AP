package com.kritjo.ap;

import java.util.ArrayList;
import java.util.Objects;

public class ProvisionContainer {
    private final ArrayList<Provision> container = new ArrayList<>();

    public void add(Provision p) {
        container.add(p);
    }

    public int sum() {
        int sumContainer = 0;
        for (Provision p : container) {
            sumContainer += p.getProvision();
        }
        return sumContainer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }

    @Override
    public boolean equals(Object other) {
        if (!other.getClass().getSimpleName().equals(this.getClass().getSimpleName())) return false;

        return sum() == ((ProvisionContainer) other).sum();
    }
}
