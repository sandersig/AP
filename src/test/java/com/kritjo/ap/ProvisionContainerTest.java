package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProvisionContainerTest {
    ProvisionContainer provisionContainer;
    ProvisionContainer provisionContainer1;
    Provision provision = new Provision(1000);
    Provision provision1 = new Provision(2000);

    @BeforeEach
    public void reset() {
        provisionContainer = new ProvisionContainer();
        provisionContainer1 = new ProvisionContainer();
    }

    @Test
    @SuppressWarnings("unchecked")
    void add() throws NoSuchFieldException, IllegalAccessException {
        Field f = provisionContainer.getClass().getDeclaredField("container");
        f.setAccessible(true);
        ArrayList<Provision> container = (ArrayList<Provision>) f.get(provisionContainer);
        assertEquals(0, container.size());
        provisionContainer.add(provision);
        assertEquals(1, container.size());
    }

    @Test
    void testHashCode() {
        int hashCodeEmpty = provisionContainer.hashCode();
        int hashCodeEmpty1 = provisionContainer1.hashCode();
        assertEquals(hashCodeEmpty, hashCodeEmpty1);
        provisionContainer.add(provision);
        assertNotEquals(hashCodeEmpty, provisionContainer.hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(provisionContainer, provisionContainer1);
        provisionContainer.add(provision);
        assertNotEquals(provisionContainer, provisionContainer1);
        provisionContainer.add(provision);
        provisionContainer1.add(provision1);
        assertEquals(provisionContainer, provisionContainer1);
    }

    @Test
    void sum() {
        assertEquals(0, provisionContainer.sum());
        provisionContainer.add(provision);
        assertEquals(1000, provisionContainer.sum());
        assertEquals(0, provisionContainer1.sum());
        provisionContainer1.add(provision1);
        assertEquals(2000, provisionContainer1.sum());

    }
}