package com.kritjo.ap.model;

import com.kritjo.ap.Checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    private final ProvisionContainer expected = new ProvisionContainer();
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
        expected.add(p);
    }

    public void addActual(Provision p) {
        actual.add(p);
    }

    public ProvisionContainer getActual(){
        return actual;
    }

    public ProvisionContainer getExpected(){
        return expected;
    }

    /**
     * @return True if the expected provision sum != actual provision sum. Else returns false.
     */
    public boolean hasDeviation() {
        return !expected.equals(actual);
    }

    public String getGSM() {
        return GSM;
    }

    public String getName() {
        return NAME;
    }

    public ArrayList<ProvisionWithNameAndGsm> getDeviations() {
        ArrayList<ProvisionWithNameAndGsm> deviationList = new ArrayList<>();
        HashMap<Float, Integer> actualCount = new HashMap<>();
        HashMap<Float, Integer> expectedCount = new HashMap<>();

        for (Provision p : actual.getContainer()) {
            if (actualCount.containsKey(p.getProvision())) {
                actualCount.put(p.getProvision(), actualCount.get(p.getProvision())+1);
            } else {
                actualCount.put(p.getProvision(), 1);
            }
        }

        for (Provision p : expected.getContainer()) {
            if (expectedCount.containsKey(p.getProvision())) {
                expectedCount.put(p.getProvision(), expectedCount.get(p.getProvision())+1);
            } else {
                expectedCount.put(p.getProvision(), 1);
            }
        }

        for (Provision p : actual.getContainer()) {
            if (!Objects.equals(actualCount.get(p.getProvision()), expectedCount.get(p.getProvision()))) {
                deviationList.add(new ProvisionWithNameAndGsm(GSM, p.getProvision(), p.getProduct(), p.getRef(), NAME, ProvisionFile.Type.ACTUAL));
            }
        }

        for (Provision p : expected.getContainer()) {
            if (!Objects.equals(actualCount.get(p.getProvision()), expectedCount.get(p.getProvision()))) {
                deviationList.add(new ProvisionWithNameAndGsm(GSM, p.getProvision(), p.getProduct(), p.getRef(), NAME, ProvisionFile.Type.EXPECTED));
            }
        }

        return deviationList;
    }
}
