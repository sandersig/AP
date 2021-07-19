package com.kritjo.ap;

public class Provision {
    private final int provision;
    private String product = "";
    private String ref = "";

    public Provision(int provision) {
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

    public int getProvision() {
        return provision;
    }
}
