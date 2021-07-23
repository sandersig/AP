package com.kritjo.ap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

/**
 * File profile object for csv files.
 */
public class CsvFile extends ProvisionFile{
    /**
     * Delimiter to be used as separator. Default is ;
     */
    private String delim = ";";
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

    public CsvFile(File file, String name, Type type) {
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
        if (gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1) throw new IllegalStateException("Set columns first");
        File profile = new File(name+".txt");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name+".txt");
            fileWriter.write("csv"+PROFILE_DELIM+delim+PROFILE_DELIM+gsmNrCol+PROFILE_DELIM+productCol+PROFILE_DELIM+refCol+PROFILE_DELIM+provisionCol+PROFILE_DELIM+nameCol+PROFILE_DELIM+startRow);
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
     * @param container that customer objects should be written to
     * @throws FileNotFoundException If the file specified in the profile does not exist.
     */
    @Override
    public void readCustomers(CustomerContainer container) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        for (int i = 0; i < startRow; i++) sc.nextLine();
        while(sc.hasNextLine()) {
            String[] line = sc.nextLine().split(delim);
            container.addCustomer(line[gsmNrCol], Float.parseFloat(line[provisionCol]), line[productCol], line[refCol], line[nameCol], type);
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
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    @Override
    public int getStartRow() {
        return startRow;
    }

    @Override
    public int getTableID() {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }
}
