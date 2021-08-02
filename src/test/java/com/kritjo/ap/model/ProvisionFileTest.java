package com.kritjo.ap.model;

import com.kritjo.ap.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ProvisionFileTest {

    @Test
    void getFileFromProfile() throws FileNotFoundException {
        try {
            ProvisionFile.getFileFromProfile("doesNotExist", new File("example.csv"), "CsvTest", ProvisionFile.Type.ACTUAL);
            assert false;
        } catch (FileNotFoundException ignore) {
        }

        CsvFile csvFile = new CsvFile(new File("example.csv"), "CsvFile", ProvisionFile.Type.ACTUAL);
        csvFile.setProductCol(1);
        csvFile.setRefCol(3);
        csvFile.setGsmNrCol(0);
        csvFile.setProvisionCol(2);
        csvFile.setNameCol(4);

        CsvFile csvFileFromProfile = (CsvFile) ProvisionFile.getFileFromProfile("csvprofile", new File("example.csv"), "CsvFile", ProvisionFile.Type.ACTUAL);

        assertTrue(new ReflectionEquals(csvFile, "").matches(csvFileFromProfile));

        ExcelFile excelFile = new ExcelFile(new File("example.xls"), "ExcelFile", ProvisionFile.Type.ACTUAL);
        excelFile.setProductCol(2);
        excelFile.setRefCol(1);
        excelFile.setGsmNrCol(3);
        excelFile.setProvisionCol(0);
        excelFile.setNameCol(4);

        ExcelFile excelFileFromProfile = (ExcelFile) ProvisionFile.getFileFromProfile("excelprofile", new File("example.xls"), "ExcelFile", ProvisionFile.Type.ACTUAL);

        assertTrue(new ReflectionEquals(excelFile, "").matches(excelFileFromProfile));

        HtmlFile htmlFile = new HtmlFile(new File("example.html"), "HtmlFile", ProvisionFile.Type.ACTUAL);
        htmlFile.setTableID(0);
        htmlFile.setProductCol(2);
        htmlFile.setRefCol(1);
        htmlFile.setGsmNrCol(3);
        htmlFile.setProvisionCol(0);
        htmlFile.setNameCol(4);

        HtmlFile htmlFileFromProfile = (HtmlFile) ProvisionFile.getFileFromProfile("htmlprofile", new File("example.html"), "HtmlFile", ProvisionFile.Type.ACTUAL);

        assertTrue(new ReflectionEquals(htmlFile, "").matches(htmlFileFromProfile));

        PdfFile pdfFile = new PdfFile(new File("example.pdf"), "PdfFile", ProvisionFile.Type.ACTUAL);
        pdfFile.setTableID(0);
        pdfFile.setProductCol(2);
        pdfFile.setRefCol(1);
        pdfFile.setGsmNrCol(3);
        pdfFile.setProvisionCol(0);
        pdfFile.setNameCol(4);

        PdfFile pdfFileFromProfile = (PdfFile) ProvisionFile.getFileFromProfile("pdfprofile", new File("example.pdf"), "PdfFile", ProvisionFile.Type.ACTUAL);

        assertTrue(new ReflectionEquals(pdfFile, "").matches(pdfFileFromProfile));

        try {
            ProvisionFile.getFileFromProfile("illegalprofile", new File("doesNotExist"), "IllegalProfile", ProvisionFile.Type.ACTUAL);
            assert false;
        } catch (IllegalArgumentException ignore) {
        }
    }

}