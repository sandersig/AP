package com.kritjo.ap;

public class Customer {
    private final String GSM;
    private final String NAME;
    private final ProvisionPool excpected = new ProvisionPool();
    private final ProvisionPool actual = new ProvisionPool();

    public Customer(String GSM, String NAME) {
        assert Checks.onlyNumbers(GSM);
        this.GSM = GSM;
        this.NAME = NAME;
    }

    public void addExpected(Provision p) {
        excpected.add(p);
    }

    public void addActual(Provision p) {
        actual.add(p);
    }

    public boolean hasDeviation() {
        return !excpected.equals(actual);
    }
}
