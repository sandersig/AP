package com.kritjo.ap.model;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

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
    public String[][] showFile() {
        // NOT IMPLEMENTED
        return new String[0][];
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
        if (tableID == -1 || gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1)
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

    /**
     * Read file and create customer objects in container.
     *
     * @param container that customer objects should be written to
     * @throws FileNotFoundException If the file specified in the profile does not exist.
     */
    @Override
    public void readCustomers(CustomerContainer container) throws IOException {
        WebClient client = new WebClient();
        HtmlPage page = client.getPage("file:" + file.getAbsolutePath());
        DomNodeList<DomElement> x = page.getElementsByTagName("table");
        HtmlTable table = (HtmlTable) x.get(tableID);

        for (int i = startRow; i < table.getRowCount(); i++) {
            HtmlTableRow row = table.getRow(i);
            HtmlTableCell gsm = row.getCell(gsmNrCol);
            HtmlTableCell provision = row.getCell(provisionCol);
            HtmlTableCell product = row.getCell(productCol);
            HtmlTableCell ref = row.getCell(refCol);
            HtmlTableCell name = row.getCell(nameCol);

            container.addCustomer(gsm.asNormalizedText(), Float.parseFloat(provision.asNormalizedText()), product.asNormalizedText(),
                    ref.asNormalizedText(), name.asNormalizedText(), type);
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
}
