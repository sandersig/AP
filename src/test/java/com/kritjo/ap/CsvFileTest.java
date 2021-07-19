package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileTest {
    CsvFile csvFile;

    @BeforeEach
    void reset() {
        csvFile = new CsvFile(new File("example.csv"), "CsvFile");
    }

    @Test
    void saveProfile() throws IOException {
        try {
            csvFile.saveProfile("testing");
            assert false;
        } catch (IllegalStateException ignore) {
        }
        csvFile.setProductCol(1);
        csvFile.setRefCol(3);
        csvFile.setGsmNrCol(0);
        csvFile.setProvisionCol(2);
        csvFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.txt"));
        String s = sc.nextLine();
        assertEquals(s, "csv-;-0-1-3-2");
        assertTrue((new File("testing.txt")).delete());
    }

    @Test
    void setDelim() throws NoSuchFieldException, IllegalAccessException {
        Field f = csvFile.getClass().getDeclaredField("delim");
        f.setAccessible(true);
        String delim = (String) f.get(csvFile);
        assertEquals(";", delim);
        csvFile.setDelim(",");
        delim = (String) f.get(csvFile);
        assertEquals(",", delim);
    }

    @Test
    void setGsmNrCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = csvFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        int gsmNrCol = (int) f.get(csvFile);
        assertEquals(-1, gsmNrCol);
        csvFile.setGsmNrCol(0);
        gsmNrCol = (int) f.get(csvFile);
        assertEquals(0, gsmNrCol);
    }

    @Test
    void setProductCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = csvFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        int productCol = (int) f.get(csvFile);
        assertEquals(-1, productCol);
        csvFile.setProductCol(0);
        productCol = (int) f.get(csvFile);
        assertEquals(0, productCol);
    }

    @Test
    void setRefCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = csvFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        int refCol = (int) f.get(csvFile);
        assertEquals(-1, refCol);
        csvFile.setRefCol(0);
        refCol = (int) f.get(csvFile);
        assertEquals(0, refCol);
    }

    @Test
    void getGsmNrCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, csvFile.getGsmNrCol());
        Field f = csvFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        f.set(csvFile, 10);
        assertEquals(10, csvFile.getGsmNrCol());
    }

    @Test
    void getProductCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, csvFile.getProductCol());
        Field f = csvFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        f.set(csvFile, 10);
        assertEquals(10, csvFile.getProductCol());
    }

    @Test
    void getRefCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, csvFile.getRefCol());
        Field f = csvFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        f.set(csvFile, 10);
        assertEquals(10, csvFile.getRefCol());
    }

    @Test
    void getDelim() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(";", csvFile.getDelim());
        Field f = csvFile.getClass().getDeclaredField("delim");
        f.setAccessible(true);
        f.set(csvFile, ",");
        assertEquals(",", csvFile.getDelim());
    }

    @Test
    void setTableID() {
        try {
            csvFile.setTableID(1);
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void getTableID() {
        try {
            csvFile.getTableID();
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void setProvisionCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = csvFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        int provisionCol = (int) f.get(csvFile);
        assertEquals(-1, provisionCol);
        csvFile.setProvisionCol(0);
        provisionCol = (int) f.get(csvFile);
        assertEquals(0, provisionCol);
    }

    @Test
    void getProvisionCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, csvFile.getProvisionCol());
        Field f = csvFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        f.set(csvFile, 10);
        assertEquals(10, csvFile.getProvisionCol());
    }
}