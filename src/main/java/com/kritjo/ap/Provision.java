package com.kritjo.ap;

public class Provision {
    private final int provision;
    private String ref = "";

    public Provision(int provision) {
        this.provision = provision;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public int getProvision() {
        return provision;
    }
}
