package com.kritjo.ap.model;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

import static java.lang.String.valueOf;

/**
 * File profile object for html files. Only one HTML table per HtmlFile object.
 */
public class HtmlFile extends ProvisionFile {
    /**
     * Table ID that contains provision table. Index start 0. -1 for not set. Non standard html format.
     */
    private int tableID = -1;
    /**
     * Column number for gsm numbers. Index start 0. -1 for not set.
     */
    private int gsmNrCol = -1;
    /**
     * Column number for product desctiption. Index start 0. -1 for not set.
     */
    private int productCol = -1;
    /**
     * Column number for a referance. Index start 0. -1 for not set.
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

    public HtmlFile(File file, String name, Type type) {
        super(file, name, type);
    }

    @Override
    public void setProvisionCol(int provisionCol) {
        this.provisionCol = provisionCol;
    }

    @Override
    public int getProvisionCol() {
        return provisionCol;
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
        WebClient client = new WebClient();
        HtmlPage page = client.getPage("file:" + file.getAbsolutePath());
        DomNodeList<DomElement> x = page.getElementsByTagName("table");
        HtmlTable table = (HtmlTable) x.get(tableID);

        ArrayList<String[]> fileRead = new ArrayList<>();

        for (int i = startRow; i < table.getRowCount(); i++) {
            HtmlTableRow row = table.getRow(i);
            ArrayList<String> rowList = new ArrayList<>();
            for (HtmlTableCell c : row.getCellIterator()) {
                rowList.add(c.asNormalizedText());
            }
            fileRead.add(rowList.toArray(new String[0]));
        }

        return fileRead.toArray(new String[0][]);
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
        if (tableID == -1 || gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1 || (brandCol == -1 && type == Type.EXPECTED))
            throw new IllegalStateException("Set columns first");
        File profile = new File(name + ".prf");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name + ".prf");
            fileWriter.write("html" + PROFILE_DELIM + tableID + PROFILE_DELIM + gsmNrCol + PROFILE_DELIM + productCol + PROFILE_DELIM + refCol + PROFILE_DELIM + provisionCol + PROFILE_DELIM + nameCol + PROFILE_DELIM + startRow);
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
     * Helper-method for readCostumers to get the table we want to read.
     * @return The table that we will want to extract information from
     * @throws IOException
     */
    public HtmlTable getTable() throws IOException {
        WebClient client = new WebClient();
        HtmlPage page = client.getPage("file:" + file.getAbsolutePath());
        DomNodeList<DomElement> x = page.getElementsByTagName("table");
        HtmlTable table = (HtmlTable) x.get(tableID);
        return table;
    }

    /**c
     * Helper-method for readCostumers that adds the information extracted from the rows in the table, into the Costumer-container.
     * @param container
     * @param row
     */
    private void createCostumerContainer(CustomerContainer container, HtmlTableRow row) {
        HtmlTableCell gsm = row.getCell(gsmNrCol);
        HtmlTableCell provision = row.getCell(provisionCol);
        HtmlTableCell product = row.getCell(productCol);
        HtmlTableCell ref = row.getCell(refCol);
        HtmlTableCell name = row.getCell(nameCol);

        container.addCustomer(gsm.asNormalizedText(), Float.parseFloat(provision.asNormalizedText()), product.asNormalizedText(),
                ref.asNormalizedText(), name.asNormalizedText(), type);
    }

    /**
     * Read file and create customer objects in container.
     *
     * Used for ACTUAl objects
     * @param container that customer objects should be written to
     * @throws FileNotFoundException If the file specified in the profile does not exist.
     */
    @Override
    public void readCustomers(CustomerContainer container) throws IOException {
        HtmlTable table = getTable();

        for (int i = startRow; i < table.getRowCount(); i++) {
            HtmlTableRow row = table.getRow(i);
            createCostumerContainer(container, row);
        }
    }

    /**
     * Read file and create customer objects in container.
     *
     * Used for EXPECTED objects
     * @param container that customer objects should be written to
     * @throws FileNotFoundException If the file specified in the profile does not exist.
     */
    @Override
    public void readCustomers(CustomerContainer container, int expectedBrandCol) throws IOException {
        HtmlTable table = getTable();

        for (int i = startRow; i < table.getRowCount(); i++) {
            HtmlTableRow row = table.getRow(i);

            if(row.getCell(brandCol).asNormalizedText() != valueOf(expectedBrandCol))
                continue;
            createCostumerContainer(container, row);
        }
    }


    @Override
    public void setDelim(String delim) {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public void setProductCol(int productCol) {
        this.productCol = productCol;
    }

    @Override
    public void setRefCol(int refCol) {
        this.refCol = refCol;
    }

    @Override
    public void setGsmNrCol(int gsmNrCol) {
        this.gsmNrCol = gsmNrCol;
    }

    @Override
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    @Override
    public int getGsmNrCol() {
        return gsmNrCol;
    }

    @Override
    public int getProductCol() {
        return productCol;
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
    public int getTableID() {
        return tableID;
    }

    @Override
    public int tableCount() throws IOException {
        WebClient client = new WebClient();
        HtmlPage page = client.getPage("file:" + file.getAbsolutePath());
        DomNodeList<DomElement> x = page.getElementsByTagName("table");
        return x.size();
    }
}
