package com.kritjo.ap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HtmlFileTest {
    HtmlFile htmlFile;

    @BeforeEach
    void reset() {
        htmlFile = new HtmlFile(new File("example.html"), "HtmlFile");
    }
    @Test
    void saveProfile() throws IOException {
        try {
            htmlFile.saveProfile("testing");
            assert false;
        } catch (IllegalStateException ignore) {
        }
        htmlFile.setTableID(0);
        htmlFile.setProductCol(2);
        htmlFile.setRefCol(1);
        htmlFile.setGsmNrCol(3);
        htmlFile.setProvisionCol(0);
        htmlFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.txt"));
        String s = sc.nextLine();
        assertEquals(s, "html-0-3-2-1-0");
        assertTrue((new File("testing.txt")).delete());
    }

    @Test
    void setDelim() {
        try {
            htmlFile.setDelim(".");
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void setRefCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = htmlFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        int refCol = (int) f.get(htmlFile);
        assertEquals(-1, refCol);
        htmlFile.setRefCol(0);
        refCol = (int) f.get(htmlFile);
        assertEquals(0, refCol);
    }

    @Test
    void setProductCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = htmlFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        int productCol = (int) f.get(htmlFile);
        assertEquals(-1, productCol);
        htmlFile.setProductCol(0);
        productCol = (int) f.get(htmlFile);
        assertEquals(0, productCol);
    }

    @Test
    void setGsmNrCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = htmlFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        int gsmNrCol = (int) f.get(htmlFile);
        assertEquals(-1, gsmNrCol);
        htmlFile.setGsmNrCol(0);
        gsmNrCol = (int) f.get(htmlFile);
        assertEquals(0, gsmNrCol);
    }

    @Test
    void getRefCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, htmlFile.getRefCol());
        Field f = htmlFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        f.set(htmlFile, 10);
        assertEquals(10, htmlFile.getRefCol());
    }

    @Test
    void getDelim() {
        try {
            htmlFile.getDelim();
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void getProductCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, htmlFile.getProductCol());
        Field f = htmlFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        f.set(htmlFile, 10);
        assertEquals(10, htmlFile.getProductCol());
    }

    @Test
    void getGsmNrCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, htmlFile.getGsmNrCol());
        Field f = htmlFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        f.set(htmlFile, 10);
        assertEquals(10, htmlFile.getGsmNrCol());
    }

    @Test
    void setProvisionCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = htmlFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        int provisionCol = (int) f.get(htmlFile);
        assertEquals(-1, provisionCol);
        htmlFile.setProvisionCol(0);
        provisionCol = (int) f.get(htmlFile);
        assertEquals(0, provisionCol);
    }

    @Test
    void getProvisionCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, htmlFile.getProvisionCol());
        Field f = htmlFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        f.set(htmlFile, 10);
        assertEquals(10, htmlFile.getProvisionCol());
    }
    @Test
    void getTableID() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, htmlFile.getTableID());
        Field f = htmlFile.getClass().getDeclaredField("tableID");
        f.setAccessible(true);
        f.set(htmlFile, 10);
        assertEquals(10, htmlFile.getTableID());
    }

    @Test
    void setTableID() throws NoSuchFieldException, IllegalAccessException {
        Field f = htmlFile.getClass().getDeclaredField("tableID");
        f.setAccessible(true);
        int tableID = (int) f.get(htmlFile);
        assertEquals(-1, tableID);
        htmlFile.setTableID(0);
        tableID = (int) f.get(htmlFile);
        assertEquals(0, tableID);
    }
}