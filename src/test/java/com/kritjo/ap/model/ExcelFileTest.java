package com.kritjo.ap.model;

import com.kritjo.ap.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileTest {

    ExcelFile excelFile;

    @BeforeEach
    void reset() {
        excelFile = new ExcelFile(new File("example.xls"), "ExcelFile", ProvisionFile.Type.ACTUAL);
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
        excelFile.setNameCol(4);
        excelFile.setDecimalSep(',');
        excelFile.setFlipNegProvCol(true);
        excelFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.prf"));
        String s = sc.nextLine();
        assertEquals(s, "excel_3_2_1_0_4_1_-1_,_true");
        sc.close();
        assertTrue((new File("testing.prf")).delete());
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

    @Test
    @SuppressWarnings("unchecked")
    void readCustomers() throws IOException, NoSuchFieldException, IllegalAccessException {
        CustomerContainer container = new CustomerContainer();
        excelFile.setGsmNrCol(0);
        excelFile.setProductCol(1);
        excelFile.setProvisionCol(2);
        excelFile.setRefCol(3);
        excelFile.setNameCol(4);
        excelFile.readCustomers(container);

        Field f = container.getClass().getDeclaredField("container");
        f.setAccessible(true);
        HashMap<String, Customer> customerHashMap = (HashMap<String, Customer>) f.get(container);

        Customer c = customerHashMap.get("12345678");
        f = c.getClass().getDeclaredField("actual");
        f.setAccessible(true);
        ProvisionContainer cProvision = (ProvisionContainer) f.get(c);

        f = cProvision.getClass().getDeclaredField("container");
        f.setAccessible(true);
        ArrayList<Provision> provisions = (ArrayList<Provision>) f.get(cProvision);

        Provision prov1 = new Provision(1000F);
        prov1.setProduct("Tele1");
        prov1.setRef("1234");

        Provision prov2 = new Provision(500F);
        prov2.setProduct("Tele2");
        prov2.setRef("4321");

        assertTrue(new ReflectionEquals(prov1, "").matches(provisions.get(0)));
        assertFalse(new ReflectionEquals(prov2, "").matches(provisions.get(1)));

        c = customerHashMap.get("98765432");
        f = c.getClass().getDeclaredField("actual");
        f.setAccessible(true);
        cProvision = (ProvisionContainer) f.get(c);

        f = cProvision.getClass().getDeclaredField("container");
        f.setAccessible(true);
        provisions = (ArrayList<Provision>) f.get(cProvision);

        Provision prov3 = new Provision(500);
        prov3.setRef("4321");
        prov3.setProduct("Tele2");

        assertTrue(new ReflectionEquals(prov3, "").matches(provisions.get(0)));
    }
}