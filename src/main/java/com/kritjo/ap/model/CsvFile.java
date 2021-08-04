package com.kritjo.ap.model;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.List;

/**
 * File profile object for csv files.
 */
public class CsvFile extends ProvisionFile {
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
    /**
     * Row number for brand name. Index start 0. -1 for not set.
     */
    private int brandCol = -1;

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
     *
     * @param name Name of the saved profile.
     * @throws IOException                If the file could not be written to.
     * @throws FileAlreadyExistsException If the file already exists.
     */
    @Override
    public void saveProfile(String name) throws IOException {
        if (gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1)
            throw new IllegalStateException("Set columns first");
        if (decimalSep == Character.MIN_VALUE) throw new IllegalStateException("Set decimal separator");
        if (type == Type.EXPECTED && brandCol == -1) throw new IllegalStateException("Set columns first");
        File profile = new File(name + ".prf");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name + ".prf");
            fileWriter.write("csv" + PROFILE_DELIM + delim + PROFILE_DELIM + gsmNrCol + PROFILE_DELIM + productCol + PROFILE_DELIM + refCol + PROFILE_DELIM + provisionCol + PROFILE_DELIM + nameCol + PROFILE_DELIM + startRow + PROFILE_DELIM + brandCol + PROFILE_DELIM + decimalSep);
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
     * Read file and create customer objects in container.
     *
     * @param container that customer objects should be written to
     * @throws FileNotFoundException If the file specified in the profile does not exist.
     */
    @Override
    public void readCustomers(CustomerContainer container) throws FileNotFoundException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.detectFormatAutomatically();
        CsvParser parser = new CsvParser(settings);
        List<String[]> rows = parser.parseAll(file);

        if (startRow > 0) {
            rows.subList(0, startRow).clear();
        }

        for (String[] line : rows) {
            container.addCustomer(line[gsmNrCol], Float.parseFloat((line[provisionCol]).replaceAll("( )", "").replaceAll(String.valueOf(decimalSep), ".")), line[productCol], line[refCol], line[nameCol], type);
        }
    }

    @Override
    public void readCustomers(CustomerContainer container, HashSet<String> payedByHK, String expectedBrand) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.detectFormatAutomatically();
        CsvParser parser = new CsvParser(settings);
        List<String[]> rows = parser.parseAll(file);

        if (startRow > 0) {
            rows.subList(0, startRow).clear();
        }

        for (String[] line : rows) {
            if(!payedByHK.isEmpty()){
                if(payedByHK.contains(line[productCol]))
                    continue;
            }
            for (int i = 0; i < line.length; i++) {
                if (line[i] == null) {
                    line[i] = "";
                }
            }

            //Går nok hella slow, men får testa
            if(line[gsmNrCol].isBlank()){
                String currentCustomer = line[nameCol];
                Integer numberOfGSMPerCustomer = 0;
                String currentGSM = null;
                for (String[] line1 : rows){
                    if(line1[nameCol].equals(currentCustomer) && !line1[gsmNrCol].isBlank() && line1[brandCol].equals(expectedBrand)){
                        currentGSM = line[gsmNrCol];
                        numberOfGSMPerCustomer++;
                    }
                }
                if(numberOfGSMPerCustomer == 1)
                    container.addCustomer(currentGSM, Float.parseFloat((line[provisionCol]).replaceAll("( )", "").replaceAll(String.valueOf(decimalSep), ".")), line[productCol], line[refCol], line[nameCol], type);
                else{
                    //TODO: Add to avviksliste for manual check
                }

            }

            if (line[brandCol].equals(expectedBrand)) {
                container.addCustomer(line[gsmNrCol], Float.parseFloat((line[provisionCol]).replaceAll("( )", "").replaceAll(String.valueOf(decimalSep), ".")), line[productCol], line[refCol], line[nameCol], type);
            }
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
    public String[][] showFile(int tableID) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.detectFormatAutomatically();
        CsvParser parser = new CsvParser(settings);
        List<String[]> rows = parser.parseAll(file);
        delim = parser.getDetectedFormat().getDelimiterString();
        return rows.toArray(new String[0][0]);
    }

    @Override
    public int getTableID() {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }
}
