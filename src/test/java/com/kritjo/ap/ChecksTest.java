package com.kritjo.ap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChecksTest {

    @Test
    void onlyNumbers() {
        assertTrue(Checks.onlyNumbers("1234"));
        assertTrue(Checks.onlyNumbers("0"));
        assertTrue(Checks.onlyNumbers("01234567891234567890"));
        assertFalse(Checks.onlyNumbers("a"));
        assertFalse(Checks.onlyNumbers("A"));
        assertFalse(Checks.onlyNumbers("ab"));
        assertFalse(Checks.onlyNumbers("AB"));
        assertFalse(Checks.onlyNumbers("Ab"));
        assertFalse(Checks.onlyNumbers("aB"));
    }
}