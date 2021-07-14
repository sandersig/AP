package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProvisionPoolTest {
    ProvisionPool provisionPool;
    ProvisionPool provisionPool1;
    Provision provision = new Provision(1000);
    Provision provision1 = new Provision(2000);

    @BeforeEach
    public void reset() {
        provisionPool = new ProvisionPool();
        provisionPool1 = new ProvisionPool();
    }

    @Test
    @SuppressWarnings("unchecked")
    void add() throws NoSuchFieldException, IllegalAccessException {
        Field f = provisionPool.getClass().getDeclaredField("pool");
        f.setAccessible(true);
        ArrayList<Provision> pool = (ArrayList<Provision>) f.get(provisionPool);
        assertEquals(0, pool.size());
        provisionPool.add(provision);
        assertEquals(1, pool.size());
    }

    @Test
    void testHashCode() {
        int hashCodeEmpty = provisionPool.hashCode();
        int hashCodeEmpty1 = provisionPool1.hashCode();
        assertEquals(hashCodeEmpty, hashCodeEmpty1);
        provisionPool.add(provision);
        assertNotEquals(hashCodeEmpty, provisionPool.hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(provisionPool, provisionPool1);
        provisionPool.add(provision);
        assertNotEquals(provisionPool, provisionPool1);
        provisionPool.add(provision);
        provisionPool1.add(provision1);
        assertEquals(provisionPool, provisionPool1);
    }

    @Test
    void sum() {
        assertEquals(0, provisionPool.sum());
        provisionPool.add(provision);
        assertEquals(1000, provisionPool.sum());
        assertEquals(0, provisionPool1.sum());
        provisionPool1.add(provision1);
        assertEquals(2000, provisionPool1.sum());

    }
}