package com.kritjo.ap.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

/**
 * File profile object for PDF files. Not implemented file reading yet.
 */
public class PdfFile extends ProvisionFile{
    /**
     * Table ID that contains provision table. Index start 0. -1 for not set. Non standard pdf format.
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

    public PdfFile(File file, String name, Type type) {
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

    /**
     * Saves the profile to file, so that it could be used in the future without setting anything up.
     * @param name Name of the saved profile.
     * @throws IOException If the file could not be written to.
     * @throws FileAlreadyExistsException If the file already exists.
     */
    @Override
    public void saveProfile(String name) throws IOException {
        if (tableID == -1 || gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1)
            throw new IllegalStateException("Set columns first");
        File profile = new File(name+".prf");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name+".prf");
            fileWriter.write("pdf"+PROFILE_DELIM+tableID+PROFILE_DELIM+gsmNrCol+PROFILE_DELIM+productCol+PROFILE_DELIM+refCol+PROFILE_DELIM+provisionCol+PROFILE_DELIM+nameCol);
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
     * Not yet a supported operation. Will be implemented soonTM.
     */
    @Override
    public void readCustomers(CustomerContainer container) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFTextStripper s = new PDFTextStripper();
        String content = s.getText(document);

        List<String> lines = new ArrayList<>();
        content.lines().forEach(str -> lines.add(str));
        System.out.println(lines.get(0));
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
