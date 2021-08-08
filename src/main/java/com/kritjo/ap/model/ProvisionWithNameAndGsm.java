package com.kritjo.ap.model;

public class ProvisionWithNameAndGsm extends Provision{
    private String gsm;
    private String name;
    private ProvisionFile.Type type;

    public ProvisionWithNameAndGsm(String gsm, float prov, String product, String ref, String name, ProvisionFile.Type type) {
        super(prov);
        super.setProduct(product);
        super.setRef(ref);
        this.gsm = gsm;
        this.name = name;
        this.type = type;
    }

    public String getGsm() {
        return gsm;
    }

    public String getName() {
        return name;
    }

    public ProvisionFile.Type getType() {
        return type;
    }
}
