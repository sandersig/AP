package com.kritjo.ap.model;

import com.kritjo.ap.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileTest {
    CsvFile csvFile;

    @BeforeEach
    void reset() {
        csvFile = new CsvFile(new File("example.csv"), "CsvFile", ProvisionFile.Type.ACTUAL);
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
        csvFile.setNameCol(4);
        csvFile.setDecimalSep(',');
        csvFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.prf"));
        String s = sc.nextLine();
        assertEquals(s, "csv_;_0_1_3_2_4_1_-1_,");
        sc.close();
        assertTrue((new File("testing.prf")).delete());
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

    @Test
    @SuppressWarnings("unchecked")
    void readCustomers() throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
        CustomerContainer container = new CustomerContainer();
        csvFile.setGsmNrCol(0);
        csvFile.setProductCol(1);
        csvFile.setProvisionCol(2);
        csvFile.setRefCol(3);
        csvFile.setNameCol(4);
        csvFile.readCustomers(container);

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

        Provision prov1 = new Provision(1000);
        prov1.setProduct("Tele1");
        prov1.setRef("1234");

        Provision prov2 = new Provision(500);
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

    @Test
    void provisionCodesWithoutGSMGetsAddedToCustomerContainerIfSimilarNameExistsInProvisionList() throws IOException {
        HashSet<String> HKCodes = new HashSet<>();

        CustomerContainer container = new CustomerContainer();
        csvFile.setGsmNrCol(0);
        csvFile.setProductCol(1);
        csvFile.setProvisionCol(2);
        csvFile.setRefCol(3);
        csvFile.setNameCol(4);
        csvFile.readCustomers(container, HKCodes,"10");
        
    }

    @Test
    void provisionCodesWithoutGSMDoesNotGetAddedToCustomerContainerIfTheCustomerNameHasOtherOrdersWithDifferentGSMs(){

    }
}