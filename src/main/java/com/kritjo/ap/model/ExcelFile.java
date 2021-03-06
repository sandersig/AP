package com.kritjo.ap.model;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * File profile object for excel files. Works for both .xls and .xlsx
 */
public class ExcelFile extends ProvisionFile {
    /**
     * Column number for gsm numbers. Index start 0. -1 for not set.
     */
    private int gsmNrCol = -1;
    /**
     * Column number for product description. Index start 0. -1 for not set.
     */
    private int productCol = -1;
    /**
     * Column number for a reference. Index start 0. -1 for not set.
     */
    private int refCol = -1;
    /**
     * Column number for provision column. Index start 0. -1 for not set.
     */
    private int provisionCol = -1;
    /**
     * Column number for customer name. Index start 0. -1 for not set.
     */
    private int nameCol = -1;
    /**
     * Row number for first row containing data. Index start 0. Default is 1, as we assume that the first row contains headers.
     */
    private int startRow = 1;
    /**
     * Row number for brand name. Index start 0. -1 for not set.
     */
    private int brandCol = -1;

    public ExcelFile(File file, String name, Type type) {
        super(file, name, type);
    }

    @Override
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    @Override
    public int getStartRow() {
        return startRow;
    }

    @Override
    public String[][] showFile(int tableID) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        ArrayList<String[]> fileRead = new ArrayList<>();

        for (Row r : sheet) {
            ArrayList<String> row = new ArrayList<>();
            for (Cell c : r) {
                c.setCellType(CellType.STRING);
                row.add(c.toString());
            }
            fileRead.add(row.toArray(new String[0]));
        }

        return fileRead.toArray(new String[0][]);
    }

    @Override
    public void setProvisionCol(int provisionCol) {
        this.provisionCol = provisionCol;
    }

    @Override
    public int getProvisionCol() {
        return provisionCol;
    }

    /**
     * Saves the profile to file, so that it could be used in the future without setting anything up.
     *
     * @param name Name of the saved profile.
     * @throws IOException                If the file could not be written to.
     * @throws FileAlreadyExistsException If the file already exists.
     */
    @Override
    public void saveProfile(String name) throws IOException {
        if (gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1)
            throw new IllegalStateException("Set columns first");
        if (type == Type.EXPECTED && brandCol == -1) throw new IllegalStateException("Set columns first");
        if (decimalSep == Character.MIN_VALUE) throw new IllegalStateException("Set decimal separator");
        File profile = new File(name + ".prf");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name + ".prf");
            fileWriter.write("excel" + PROFILE_DELIM + gsmNrCol + PROFILE_DELIM + productCol + PROFILE_DELIM + refCol + PROFILE_DELIM + provisionCol + PROFILE_DELIM + nameCol + PROFILE_DELIM + startRow + PROFILE_DELIM + brandCol + PROFILE_DELIM + decimalSep + PROFILE_DELIM + flipNegProvCol);
            fileWriter.close();
        } else {
            throw new FileAlreadyExistsException("File already exists");
        }
    }

    @Override
    public void setNameCol(int nameCol) {
        this.nameCol = nameCol;
    }

    @Override
    public int getNameCol() {
        return nameCol;
    }

    @Override
    public void setBrandCol(int brandCol) {
        this.brandCol = brandCol;
    }

    @Override
    public int getBrandCol() {
        return brandCol;
    }

    /**
     * Helper-method to readCostumers for retrieving an iterator for the Excel-tables.
     * @return An iterator
     * @throws IOException
     */
    private Iterator<Row> getTableIterator() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> it = sheet.rowIterator();
        return it;
    }

    /**
     * Helper-method for readCostumers that adds the information extracted from the rows in the table, into the Costumer-container.
     * @param container
     * @param row
     */
    private void createCostumerContainer(CustomerContainer container, Row row, HashSet<String> productManual){
        Cell gsm = row.getCell(gsmNrCol);
        gsm.setCellType(CellType.STRING);
        Cell provision = row.getCell(provisionCol);
        provision.setCellType(CellType.NUMERIC);
        Cell product = row.getCell(productCol);
        product.setCellType(CellType.STRING);
        Cell name = row.getCell(nameCol);
        name.setCellType(CellType.STRING);
        Cell ref = row.getCell(refCol);
        ref.setCellType(CellType.STRING);

        String gsmConverted = gsm.toString();
        float provisionConverted = Float.parseFloat(provision.toString().replaceAll("( )", "").replaceAll(String.valueOf(decimalSep), "."));
        if (flipNegProvCol) provisionConverted *= -1;

        String productConverted = product.toString();
        String nameConverted = name.toString();
        String refConverted = ref.toString();

        if (productManual.contains(productConverted)) {
            container.addManual(gsmConverted, provisionConverted, productConverted, refConverted, nameConverted, type);
        } else {
            container.addCustomer(gsmConverted, provisionConverted, productConverted, refConverted, nameConverted, type);
        }
    }

    @Override
    public void readCustomers(CustomerContainer container, HashSet<String> productManual) throws IOException {
        Iterator<Row> it = getTableIterator();

        for (int i = 0; i < startRow; i++)
            it.next();

        while (it.hasNext()) {
            Row row = it.next();
            createCostumerContainer(container, row, productManual);
        }
    }
    @Override
    public void readCustomers(CustomerContainer container, HashSet<String> payedByHK, String expectedBrand, HashSet<String> productManual) throws IOException {
        Iterator<Row> it = getTableIterator();

        for (int i = 0; i < startRow; i++)
            it.next();

        while (it.hasNext()) {
            Row row = it.next();

            Cell brand = row.getCell(brandCol);
            brand.setCellType(CellType.STRING);
            String brandConverted = brand.toString();

            if (!brandConverted.equals(expectedBrand))
                continue;
            if(!payedByHK.isEmpty()){
                Cell product = row.getCell(productCol);
                product.setCellType(CellType.STRING);
                String productConverted = product.toString();
                if(payedByHK.contains(productConverted))
                    continue;
            }
            createCostumerContainer(container, row, productManual);
        }
    }

    @Override
    public void setDelim(String delim) {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public void setRefCol(int refCol) {
        this.refCol = refCol;
    }

    @Override
    public void setProductCol(int productCol) {
        this.productCol = productCol;
    }

    @Override
    public void setGsmNrCol(int gsmNrCol) {
        this.gsmNrCol = gsmNrCol;
    }

    @Override
    public int getRefCol() {
        return refCol;
    }

    @Override
    public String getDelim() {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public void setTableID(int tableID) {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public int getTableID() {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public int getProductCol() {
        return productCol;
    }

    @Override
    public int getGsmNrCol() {
        return gsmNrCol;
    }
}
