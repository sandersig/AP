package com.kritjo.ap;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ProvisionFileTest {

    @Test
    void getFileFromProfile() throws FileNotFoundException {
        try {
            ProvisionFile.getFileFromProfile("doesNotExist", new File("example.csv"), "CsvTest");
            assert false;
        } catch (FileNotFoundException ignore) {
        }

        CsvFile csvFile = new CsvFile(new File("example.csv"), "CsvFile");
        csvFile.setProductCol(1);
        csvFile.setRefCol(3);
        csvFile.setGsmNrCol(0);
        csvFile.setProvisionCol(2);

        CsvFile csvFileFromProfile = (CsvFile) ProvisionFile.getFileFromProfile("csvprofile", new File("example.csv"), "CsvFile");

        assertTrue(new ReflectionEquals(csvFile, "").matches(csvFileFromProfile));

        ExcelFile excelFile = new ExcelFile(new File("example.xls"), "ExcelFile");
        excelFile.setProductCol(2);
        excelFile.setRefCol(1);
        excelFile.setGsmNrCol(3);
        excelFile.setProvisionCol(0);

        ExcelFile excelFileFromProfile = (ExcelFile) ProvisionFile.getFileFromProfile("excelprofile", new File("example.xls"), "ExcelFile");

        assertTrue(new ReflectionEquals(excelFile, "").matches(excelFileFromProfile));

        HtmlFile htmlFile = new HtmlFile(new File("example.html"), "HtmlFile");
        htmlFile.setTableID(0);
        htmlFile.setProductCol(2);
        htmlFile.setRefCol(1);
        htmlFile.setGsmNrCol(3);
        htmlFile.setProvisionCol(0);

        HtmlFile htmlFileFromProfile = (HtmlFile) ProvisionFile.getFileFromProfile("htmlprofile", new File("example.html"), "HtmlFile");

        assertTrue(new ReflectionEquals(htmlFile, "").matches(htmlFileFromProfile));

        PdfFile pdfFile = new PdfFile(new File("example.pdf"), "PdfFile");
        pdfFile.setTableID(0);
        pdfFile.setProductCol(2);
        pdfFile.setRefCol(1);
        pdfFile.setGsmNrCol(3);
        pdfFile.setProvisionCol(0);

        PdfFile pdfFileFromProfile = (PdfFile) ProvisionFile.getFileFromProfile("pdfprofile", new File("example.pdf"), "PdfFile");

        assertTrue(new ReflectionEquals(pdfFile, "").matches(pdfFileFromProfile));

        try {
            ProvisionFile.getFileFromProfile("illegalprofile", new File("doesNotExist"), "IllegalProfile");
            assert false;
        } catch (IllegalArgumentException ignore) {
        }
    }

}