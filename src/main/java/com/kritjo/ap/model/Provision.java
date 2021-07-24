package com.kritjo.ap.model;

/**
 * Provision object for one "line"/kickback of provision.
 */
public class Provision {
    private final float provision;
    private String product = "";
    private String ref = "";

    public Provision(float provision) {
        this.provision = provision;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public float getProvision() {
        return provision;
    }
}
