package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerContainerTest {
    CustomerContainer customerContainer;
    CustomerContainer customerContainer1;

    Provision provision100;
    Provision provision1000;

    Customer customerWithoutDeviation;
    Customer customerWithDeviation;
    Customer[] customersWithDeviation;
    Customer[] customersWithoutDeviation;

    CustomerContainerTest() {
        provision100 = new Provision(100);
        provision1000 = new Provision(1000);
        provision100.setProduct("A");
        provision100.setRef("1234");
        provision1000.setProduct("B");
        provision1000.setRef("4321");
    }

    @BeforeEach
    void reset() {
        customerContainer = new CustomerContainer();
        customerContainer1 = new CustomerContainer();

        customerWithoutDeviation = new Customer("123456789", "kritjo");
        customerWithoutDeviation.addExpected(provision100);
        customerWithoutDeviation.addActual(provision100);

        customerContainer.addCustomer(customerWithoutDeviation);

        customerWithDeviation = new Customer("876454321", "sandersig");
        customerWithDeviation.addActual(provision1000);
        customerWithDeviation.addExpected(provision100);

        customerContainer.addCustomer(customerWithDeviation);

        customersWithoutDeviation = new Customer[10];
        for (int i = 0; i < 10; i++) {
           customersWithoutDeviation[i] = customerWithoutDeviation;
        }

        customerContainer1.addCustomers(customersWithoutDeviation);

        customersWithDeviation = new Customer[10];
        for (int i = 0; i < 10; i++) {
            customersWithDeviation[i] = customerWithDeviation;
        }

        customerContainer1.addCustomers(customersWithDeviation);
    }

    @Test
    void getDeviations() {
        ArrayList<Customer> deviatingCustomers = customerContainer.getDeviations();
        assertEquals(deviatingCustomers.get(0), customerWithDeviation);

        deviatingCustomers = customerContainer1.getDeviations();
        for (Customer c : deviatingCustomers) {
            assertEquals(c, customerWithDeviation);
        }
    }
}