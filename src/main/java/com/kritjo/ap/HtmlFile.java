package com.kritjo.ap;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class HtmlFile extends ProvisionFile{
    private int tableID = -1;
    private int gsmNrCol = -1;
    private int productCol = -1;
    private int refCol = -1;
    private int provisionCol = -1;
    private int nameCol = -1;
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
    public void saveProfile(String name) throws IOException {
        if (tableID == -1 || gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1) throw new IllegalStateException("Set columns first");
        File profile = new File(name+".txt");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name+".txt");
            fileWriter.write("html"+PROFILE_DELIM+tableID+PROFILE_DELIM+gsmNrCol+PROFILE_DELIM+productCol+PROFILE_DELIM+refCol+PROFILE_DELIM+provisionCol+PROFILE_DELIM+nameCol+PROFILE_DELIM+startRow);
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
    public void readCustomers(CustomerContainer container) throws IOException {
        WebClient client = new WebClient();
        HtmlPage page = client.getPage("file:"+file.getAbsolutePath());
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
