package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ProvisionTest {
    private Provision provision;

    @BeforeEach
    public void reset() {
        provision = new Provision(1000);
    }

    @Test
    void setRef() throws NoSuchFieldException, IllegalAccessException {
        provision.setRef("ref1");
        Field f = provision.getClass().getDeclaredField("ref");
        f.setAccessible(true);
        assertEquals("ref1", f.get(provision));
    }

    @Test
    void getRef() throws NoSuchFieldException, IllegalAccessException {
        assertEquals("", provision.getRef());
        Field f = provision.getClass().getDeclaredField("ref");
        f.setAccessible(true);
        f.set(provision, "ref1");
        assertEquals("ref1", provision.getRef());
        f.set(provision, "");
        assertEquals("", provision.getRef());
    }

    @Test
    void getProvision() {
        assertEquals(1000, provision.getProvision());
        Provision provision1 = new Provision(0);
        assertEquals(0, provision1.getProvision());
        Provision provision2 = new Provision(-1000);
        assertEquals(-1000, provision2.getProvision());
    }
}