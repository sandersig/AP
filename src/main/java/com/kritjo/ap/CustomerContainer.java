package com.kritjo.ap;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerContainer {
    private final HashMap<String, Customer> container = new HashMap<>();

    public ArrayList<Customer> getDeviations() {
        ArrayList<Customer> deviatingCustomers = new ArrayList<>();
        for (Customer c : container.values()) {
            if (c.hasDeviation()) {
                deviatingCustomers.add(c);
            }
        }
        return deviatingCustomers;
    }

    public void addCustomer(String gsm, float provision, String product, String ref, String name, ProvisionFile.Type type) {
        Provision newProvision = new Provision(provision);
        newProvision.setRef(ref);
        newProvision.setProduct(product);
        newProvision.setProduct(product);
        if (container.containsKey(gsm)) {
            Customer existingCustomer = container.get(gsm);
            if (type == ProvisionFile.Type.ACTUAL) {
                existingCustomer.addActual(newProvision);
            } else {
                existingCustomer.addExpected(newProvision);
            }
        } else {
            Customer newCustomer = new Customer(gsm, name);
            if (type == ProvisionFile.Type.ACTUAL) {
                newCustomer.addActual(newProvision);
            } else {
                newCustomer.addExpected(newProvision);
            }
            container.put(gsm, newCustomer);
        }
    }
}
