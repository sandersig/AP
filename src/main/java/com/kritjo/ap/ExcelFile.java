package com.kritjo.ap;

import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Iterator;

public class ExcelFile extends ProvisionFile{
    private int gsmNrCol = -1;
    private int productCol = -1;
    private int refCol = -1;
    private int provisionCol = -1;
    private int nameCol = -1;
    private int startRow = 1;

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
    public void setProvisionCol(int provisionCol) {
        this.provisionCol = provisionCol;
    }

    @Override
    public int getProvisionCol() {
        return provisionCol;
    }

    @Override
    public void saveProfile(String name) throws IOException {
        if (gsmNrCol == -1 || productCol == -1 || refCol == -1 || provisionCol == -1 || nameCol == -1) throw new IllegalStateException("Set columns first");
        File profile = new File(name+".txt");
        if (profile.createNewFile()) {
            FileWriter fileWriter = new FileWriter(name+".txt");
            fileWriter.write("excel"+PROFILE_DELIM+gsmNrCol+PROFILE_DELIM+productCol+PROFILE_DELIM+refCol+PROFILE_DELIM+provisionCol+PROFILE_DELIM+nameCol+PROFILE_DELIM+startRow);
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
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> it = sheet.rowIterator();

        for (int i = 0; i < startRow; i++) {
            it.next();
        }

        while (it.hasNext()) {
            Row row = it.next();
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
            float procisionConverted = Float.parseFloat(provision.toString());
            String productConverted = product.toString();
            String nameConverted = name.toString();
            String refConverted = ref.toString();

            container.addCustomer(gsmConverted, procisionConverted, productConverted, refConverted, nameConverted, type);
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
