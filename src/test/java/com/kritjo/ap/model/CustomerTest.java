package com.kritjo.ap.model;

import com.kritjo.ap.model.Customer;
import com.kritjo.ap.model.Provision;
import com.kritjo.ap.model.ProvisionContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer customer;
    Provision provision = new Provision(1000);
    Provision provision1 = new Provision(2000);

    @BeforeEach
    public void reset() {
        customer = new Customer("47645330", "Kristian Tjelta Johansen");
    }

    @Test
    @SuppressWarnings("unchecked")
    void addExpected() throws NoSuchFieldException, IllegalAccessException {
        Field f = customer.getClass().getDeclaredField("expected");
        f.setAccessible(true);
        ProvisionContainer expected = (ProvisionContainer) f.get(customer);
        customer.addExpected(provision);
        Field pf = expected.getClass().getDeclaredField("container");
        pf.setAccessible(true);
        ArrayList<Provision> expectedArray = (ArrayList<Provision>) pf.get(expected);
        assertEquals(provision, expectedArray.get(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void addActual() throws NoSuchFieldException, IllegalAccessException {
        Field f = customer.getClass().getDeclaredField("actual");
        f.setAccessible(true);
        ProvisionContainer actual = (ProvisionContainer) f.get(customer);
        customer.addActual(provision);
        Field pf = actual.getClass().getDeclaredField("container");
        pf.setAccessible(true);
        ArrayList<Provision> actualArray = (ArrayList<Provision>) pf.get(actual);
        assertEquals(provision, actualArray.get(0));
    }

    @Test
    void hasDeviation() {
        customer.addActual(provision);
        customer.addExpected(provision);
        assertFalse(customer.hasDeviation());
        customer.addActual(provision);
        assertTrue(customer.hasDeviation());
        customer.addExpected(provision1);
        assertTrue(customer.hasDeviation());
    }
}