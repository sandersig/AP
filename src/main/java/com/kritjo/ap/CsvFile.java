package com.kritjo.ap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class CsvFile extends ProvisionFile{
    private String delim = ";";
    private int gsmNrCol = -1;
    private int productCol = -1;
    private int refCol = -1;
    private int provisionCol = -1;

    public CsvFile(File file, String name) {
        super(file, name);
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
    public void saveProfile(String name) throws IOException {
        if (gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1) throw new IllegalStateException("Set columns first");
        File profile = new File(name+".txt");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name+".txt");
            fileWriter.write("csv"+PROFILE_DELIM+delim+PROFILE_DELIM+gsmNrCol+PROFILE_DELIM+productCol+PROFILE_DELIM+refCol+PROFILE_DELIM+provisionCol);
            fileWriter.close();
        } else {
            throw new FileAlreadyExistsException("File already exists");
        }
    }

    @Override
    public void setDelim(String delim) {
        this.delim = delim;
    }

    @Override
    public void setGsmNrCol(int gsmNrCol) {
        this.gsmNrCol = gsmNrCol;
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
        return delim;
    }

    @Override
    public void setTableID(int tableID) {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public int getTableID() {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }
}
