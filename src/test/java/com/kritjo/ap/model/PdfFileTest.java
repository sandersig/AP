package com.kritjo.ap.model;

import com.kritjo.ap.model.PdfFile;
import com.kritjo.ap.model.ProvisionFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PdfFileTest {
    PdfFile pdfFile;

    @BeforeEach
    void reset() {
        pdfFile = new PdfFile(new File("example.pdf"), "pdfFile", ProvisionFile.Type.ACTUAL);
    }

    @Test
    void saveProfile() throws IOException {
        try {
            pdfFile.saveProfile("testing");
            assert false;
        } catch (IllegalStateException ignore) {
        }
        pdfFile.setTableID(0);
        pdfFile.setProductCol(2);
        pdfFile.setRefCol(1);
        pdfFile.setGsmNrCol(3);
        pdfFile.setProvisionCol(0);
        pdfFile.setNameCol(4);
        pdfFile.setDecimalSep(',');
        pdfFile.setFlipNegProvCol(true);
        pdfFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.prf"));
        String s = sc.nextLine();
        assertEquals(s, "pdf_0_3_2_1_0_4_-1_,_true");
        sc.close();
        assertTrue((new File("testing.prf")).delete());
    }

    @Test
    void setDelim() {
        try {
            pdfFile.setDelim(".");
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void setRefCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = pdfFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        int refCol = (int) f.get(pdfFile);
        assertEquals(-1, refCol);
        pdfFile.setRefCol(0);
        refCol = (int) f.get(pdfFile);
        assertEquals(0, refCol);
    }

    @Test
    void setProductCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = pdfFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        int productCol = (int) f.get(pdfFile);
        assertEquals(-1, productCol);
        pdfFile.setProductCol(0);
        productCol = (int) f.get(pdfFile);
        assertEquals(0, productCol);
    }

    @Test
    void setGsmNrCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = pdfFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        int gsmNrCol = (int) f.get(pdfFile);
        assertEquals(-1, gsmNrCol);
        pdfFile.setGsmNrCol(0);
        gsmNrCol = (int) f.get(pdfFile);
        assertEquals(0, gsmNrCol);
    }

    @Test
    void getRefCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, pdfFile.getRefCol());
        Field f = pdfFile.getClass().getDeclaredField("refCol");
        f.setAccessible(true);
        f.set(pdfFile, 10);
        assertEquals(10, pdfFile.getRefCol());
    }

    @Test
    void getDelim() {
        try {
            pdfFile.getDelim();
            assert false;
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    void getProductCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, pdfFile.getProductCol());
        Field f = pdfFile.getClass().getDeclaredField("productCol");
        f.setAccessible(true);
        f.set(pdfFile, 10);
        assertEquals(10, pdfFile.getProductCol());
    }

    @Test
    void getGsmNrCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, pdfFile.getGsmNrCol());
        Field f = pdfFile.getClass().getDeclaredField("gsmNrCol");
        f.setAccessible(true);
        f.set(pdfFile, 10);
        assertEquals(10, pdfFile.getGsmNrCol());
    }

    @Test
    void setProvisionCol() throws NoSuchFieldException, IllegalAccessException {
        Field f = pdfFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        int provisionCol = (int) f.get(pdfFile);
        assertEquals(-1, provisionCol);
        pdfFile.setProvisionCol(0);
        provisionCol = (int) f.get(pdfFile);
        assertEquals(0, provisionCol);
    }

    @Test
    void getProvisionCol() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, pdfFile.getProvisionCol());
        Field f = pdfFile.getClass().getDeclaredField("provisionCol");
        f.setAccessible(true);
        f.set(pdfFile, 10);
        assertEquals(10, pdfFile.getProvisionCol());
    }
    @Test
    void getTableID() throws IllegalAccessException, NoSuchFieldException {
        assertEquals(-1, pdfFile.getTableID());
        Field f = pdfFile.getClass().getDeclaredField("tableID");
        f.setAccessible(true);
        f.set(pdfFile, 10);
        assertEquals(10, pdfFile.getTableID());
    }

    @Test
    void setTableID() throws NoSuchFieldException, IllegalAccessException {
        Field f = pdfFile.getClass().getDeclaredField("tableID");
        f.setAccessible(true);
        int tableID = (int) f.get(pdfFile);
        assertEquals(-1, tableID);
        pdfFile.setTableID(0);
        tableID = (int) f.get(pdfFile);
        assertEquals(0, tableID);
    }

    CustomerContainer container = new CustomerContainer();
    @Test
    void checkIfTextIsReadInCorrectly() throws IOException {
        pdfFile.readCustomers(container);
    }

    @Test
    void pdfFileGetsReadCorrectly(){

    }

}