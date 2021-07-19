package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileTest {

    ExcelFile excelFile;

    @BeforeEach
    void reset() {
        excelFile = new ExcelFile(new File("example.csv"), "CsvFile");
    }

    @Test
    void saveProfile() throws IOException {
        try {
            excelFile.saveProfile("testing");
            assert false;
        } catch (IllegalStateException ignore) {
        }
        excelFile.setProductCol(2);
        excelFile.setRefCol(1);
        excelFile.setGsmNrCol(3);
        excelFile.setProvisionCol(0);
        excelFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.txt"));
        String s = sc.nextLine();
        assertEquals(s, "excel-3-2-1-0");
        assertTrue((new File("testing.txt")).delete());
    }

    @Test
    void setDelim() {
        try {
            excelFile.setDelim(".");
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void setRefCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = excelFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        int refCol = (int) f.get(excelFile);
        assertEquals(-1, refCol);
        excelFile.setRefCol(0);
        refCol = (int) f.get(excelFile);
        assertEquals(0, refCol);
    }

    @Test
    void setProductCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = excelFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        int productCol = (int) f.get(excelFile);
        assertEquals(-1, productCol);
        excelFile.setProductCol(0);
        productCol = (int) f.get(excelFile);
        assertEquals(0, productCol);
    }

    @Test
    void setGsmNrCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = excelFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        int gsmNrCol = (int) f.get(excelFile);
        assertEquals(-1, gsmNrCol);
        excelFile.setGsmNrCol(0);
        gsmNrCol = (int) f.get(excelFile);
        assertEquals(0, gsmNrCol);
    }

    @Test
    void getRefCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, excelFile.getRefCol());
        Field f = excelFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        f.set(excelFile, 10);
        assertEquals(10, excelFile.getRefCol());
    }

    @Test
    void getDelim() {
        try {
            excelFile.getDelim();
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void setTableID() {
        try {
            excelFile.setTableID(1);
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void getTableID() {
        try {
            excelFile.getTableID();
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void getProductCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, excelFile.getProductCol());
        Field f = excelFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        f.set(excelFile, 10);
        assertEquals(10, excelFile.getProductCol());
    }

    @Test
    void getGsmNrCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, excelFile.getGsmNrCol());
        Field f = excelFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        f.set(excelFile, 10);
        assertEquals(10, excelFile.getGsmNrCol());
    }

    @Test
    void setProvisionCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = excelFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        int provisionCol = (int) f.get(excelFile);
        assertEquals(-1, provisionCol);
        excelFile.setProvisionCol(0);
        provisionCol = (int) f.get(excelFile);
        assertEquals(0, provisionCol);
    }

    @Test
    void getProvisionCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, excelFile.getProvisionCol());
        Field f = excelFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        f.set(excelFile, 10);
        assertEquals(10, excelFile.getProvisionCol());
    }
}