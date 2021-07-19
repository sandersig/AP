package com.kritjo.ap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public abstract class ProvisionFile {
    public static final String PROFILE_DELIM = "-";
    private File file;
    private String name;

    public ProvisionFile(File file, String name) {
        this.file = file;
        this.name = name;
    }

    public static ProvisionFile getFileFromProfile(String profileName, File provisionFile, String name) throws FileNotFoundException {
        File file = new File(profileName+".txt");
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        sc.close();
        String[] profile = line.split(PROFILE_DELIM);
        ProvisionFile provisionFileFromProfile;
        switch (profile[0]) {
            case "csv" -> {
                provisionFileFromProfile = new CsvFile(provisionFile, name);
                provisionFileFromProfile.setDelim(profile[1]);
                provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[2]));
                provisionFileFromProfile.setProductCol(Integer.parseInt(profile[3]));
                provisionFileFromProfile.setRefCol(Integer.parseInt(profile[4]));
                provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[5]));
            }
            case "html" -> {
                provisionFileFromProfile = new HtmlFile(provisionFile, name);
                pdfHtmlSet(profile, provisionFileFromProfile);
            }
            case "pdf" -> {
                provisionFileFromProfile = new PdfFile(provisionFile, name);
                pdfHtmlSet(profile, provisionFileFromProfile);
            }
            case "excel" -> {
                provisionFileFromProfile = new ExcelFile(provisionFile, name);
                provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[1]));
                provisionFileFromProfile.setProductCol(Integer.parseInt(profile[2]));
                provisionFileFromProfile.setRefCol(Integer.parseInt(profile[3]));
                provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[4]));
            }
            default -> throw new IllegalArgumentException("Provided profile has wrong formating.");
        }
        return provisionFileFromProfile;
    }

    private static void pdfHtmlSet(String[] profile, ProvisionFile provisionFileFromProfile) {
        provisionFileFromProfile.setTableID(Integer.parseInt(profile[1]));
        provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[2]));
        provisionFileFromProfile.setProductCol(Integer.parseInt(profile[3]));
        provisionFileFromProfile.setRefCol(Integer.parseInt(profile[4]));
        provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[5]));
    }

    public abstract void setProvisionCol(int provisionCol);

    public abstract int getProvisionCol();

    public abstract void setDelim(String delim);

    public abstract void setGsmNrCol(int gsmNrCol);

    public abstract void setProductCol(int productCol);

    public abstract void setRefCol(int refCol);

    public abstract int getGsmNrCol();

    public abstract int getProductCol();

    public abstract int getRefCol();

    public abstract String getDelim();

    public abstract void setTableID(int tableID);

    public abstract int getTableID();

    public abstract void saveProfile(String name) throws IOException;
}
