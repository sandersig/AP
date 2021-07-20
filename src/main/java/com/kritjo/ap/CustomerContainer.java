package com.kritjo.ap;

import java.util.ArrayList;
import java.util.Collections;

public class CustomerContainer {
    private final ArrayList<Customer> container = new ArrayList<>();

    public ArrayList<Customer> getDeviations() {
        ArrayList<Customer> deviatingCustomers = new ArrayList<>();
        for (Customer c : container) {
            if (c.hasDeviation()) {
                deviatingCustomers.add(c);
            }
        }
        return deviatingCustomers;
    }

    public void addCustomer(Customer c) {
        container.add(c);
    }

    public void addCustomers(Customer[] customers) {
        Collections.addAll(container, customers);
    }
}
