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
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HtmlFileTest {
    HtmlFile htmlFile;
    HtmlFile htmlFileWithHKCodes;

    @BeforeEach
    void reset() {
        htmlFile = new HtmlFile(new File("example.html"), "HtmlFile", ProvisionFile.Type.ACTUAL);
        htmlFileWithHKCodes = new HtmlFile(new File("exampleWithHKCodes.html"), "HtmlFileWithHKCodes", ProvisionFile.Type.EXPECTED);
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
        htmlFile.setNameCol(4);
        htmlFile.saveProfile("testing");
        Scanner sc = new Scanner(new File("testing.prf"));
        String s = sc.nextLine();
        assertEquals(s, "html_0_3_2_1_0_4_1_-1");
        sc.close();
        assertTrue((new File("testing.prf")).delete());
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
    void setBrandCol() throws NoSuchFieldException, IllegalAccessException {

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

    @Test
    @SuppressWarnings("unchecked")
    void readCustomers() throws IOException, NoSuchFieldException, IllegalAccessException {
        CustomerContainer container = new CustomerContainer();
        htmlFile.setGsmNrCol(0);
        htmlFile.setProductCol(1);
        htmlFile.setProvisionCol(2);
        htmlFile.setRefCol(3);
        htmlFile.setNameCol(4);
        htmlFile.setTableID(0);
        htmlFile.readCustomers(container);

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
        prov1.setProduct("stntyv");
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

    @Test
    void rowsIncludingCodesThatIsPayedByHKGetsSkippedByReadCostumer() throws IOException {
        HashSet<String> HKCodes = new HashSet<>();
        HKCodes.add("stntyv");
        HKCodes.add("swappluss");

        CustomerContainer container = new CustomerContainer();
        htmlFileWithHKCodes.setGsmNrCol(0);
        htmlFileWithHKCodes.setProductCol(1);
        htmlFileWithHKCodes.setProvisionCol(2);
        htmlFileWithHKCodes.setRefCol(3);
        htmlFileWithHKCodes.setNameCol(4);
        htmlFileWithHKCodes.setTableID(0);
       // htmlFileWithHKCodes.setBrandCol(10); //test value
        htmlFileWithHKCodes.readCustomers(container, HKCodes, "033");

        assertEquals(3, container.getContainerSize());
    }
}