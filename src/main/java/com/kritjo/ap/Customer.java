package com.kritjo.ap;

/**
 * Customer object. GSM is unique ID. Contains two ProvisionContainers for expected and actual provision.
 */
public class Customer {
    /**
     * Customers GSM number. Used as Unique ID.
     */
    private final String GSM;
    /**
     * Customer name.
     */
    private final String NAME;
    /**
     * Expected provision, from source sales system.
     */
    private final ProvisionContainer excpected = new ProvisionContainer();
    /**
     * Actual provision from third party controller system.
     */
    private final ProvisionContainer actual = new ProvisionContainer();

    public Customer(String GSM, String NAME) {
        // Check that the GSM only contain numbers. Saved as a String in order to manage leading 0.
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

    /**
     * @return True if the expected provision sum != actual provision sum. Else returns false.
     */
    public boolean hasDeviation() {
        return !excpected.equals(actual);
    }
}
