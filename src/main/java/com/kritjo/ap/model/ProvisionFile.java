package com.kritjo.ap.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Abstract implementation of file profiles.
 */
public abstract class ProvisionFile {
    /**
     * Constant that specifies the delimiter used in saved profiles.
     */
    public static final String PROFILE_DELIM = "_";
    /**
     * Actual file that should use the profile.
     */
    protected File file;
    /**
     * Name of this profile instance e.g. Telenor CSV 2020.
     */
    private String name;
    /**
     * Actual or expected provision.
     */
    protected Type type;

    public ProvisionFile(File file, String name, Type type) {
        this.file = file;
        this.name = name;
        this.type = type;
    }

    /**
     * @param profileName   Name of saved profile
     * @param provisionFile Actual file to use profile on
     * @param name          Name of this profile instance.
     * @return a ProvisionFile of the actual subclass type.
     * @throws FileNotFoundException If no profile with the provided name has been found.
     */
    public static ProvisionFile getFileFromProfile(String profileName, File provisionFile, String name) throws FileNotFoundException {
        File file = new File(profileName + ".prf");
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        sc.close();
        String[] profile = line.split(PROFILE_DELIM);
        ProvisionFile provisionFileFromProfile;
        switch (profile[0]) {
            case "csv" -> {
                provisionFileFromProfile = new CsvFile(provisionFile, name, Type.ACTUAL);
                provisionFileFromProfile.setDelim(profile[1]);
                provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[2]));
                provisionFileFromProfile.setProductCol(Integer.parseInt(profile[3]));
                provisionFileFromProfile.setRefCol(Integer.parseInt(profile[4]));
                provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[5]));
                provisionFileFromProfile.setNameCol(Integer.parseInt(profile[6]));
                provisionFileFromProfile.setStartRow(Integer.parseInt(profile[7]));
                provisionFileFromProfile.setBrandCol(Integer.parseInt(profile[8]));
            }
            case "html" -> {
                provisionFileFromProfile = new HtmlFile(provisionFile, name, Type.ACTUAL);
                provisionFileFromProfile.setTableID(Integer.parseInt(profile[1]));
                provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[2]));
                provisionFileFromProfile.setProductCol(Integer.parseInt(profile[3]));
                provisionFileFromProfile.setRefCol(Integer.parseInt(profile[4]));
                provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[5]));
                provisionFileFromProfile.setNameCol(Integer.parseInt(profile[6]));
                provisionFileFromProfile.setStartRow(Integer.parseInt(profile[7]));
                provisionFileFromProfile.setBrandCol(Integer.parseInt(profile[8]));
            }
            case "pdf" -> {
                provisionFileFromProfile = new PdfFile(provisionFile, name, Type.ACTUAL);
                provisionFileFromProfile.setTableID(Integer.parseInt(profile[1]));
                provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[2]));
                provisionFileFromProfile.setProductCol(Integer.parseInt(profile[3]));
                provisionFileFromProfile.setRefCol(Integer.parseInt(profile[4]));
                provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[5]));
                provisionFileFromProfile.setNameCol(Integer.parseInt(profile[6]));
                provisionFileFromProfile.setBrandCol(Integer.parseInt(profile[7]));
            }
            case "excel" -> {
                provisionFileFromProfile = new ExcelFile(provisionFile, name, Type.ACTUAL);
                provisionFileFromProfile.setGsmNrCol(Integer.parseInt(profile[1]));
                provisionFileFromProfile.setProductCol(Integer.parseInt(profile[2]));
                provisionFileFromProfile.setRefCol(Integer.parseInt(profile[3]));
                provisionFileFromProfile.setProvisionCol(Integer.parseInt(profile[4]));
                provisionFileFromProfile.setNameCol(Integer.parseInt(profile[5]));
                provisionFileFromProfile.setStartRow(Integer.parseInt(profile[6]));
                provisionFileFromProfile.setBrandCol(Integer.parseInt(profile[7]));
            }
            default -> throw new IllegalArgumentException("Provided profile has wrong formating.");
        }
        return provisionFileFromProfile;
    }

    public static File[] existingProfiles() {
        ArrayList<File> existingProfiles = new ArrayList<>();
        File userDirectory = new File(System.getProperty("user.dir"));
        for (File entry : Objects.requireNonNull(userDirectory.listFiles(), "no files in working dir")) {
            if (entry.getName().endsWith(".prf")) {
                existingProfiles.add(entry);
            }
        }
        return existingProfiles.toArray(new File[0]);
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

    public abstract void setNameCol(int nameCol);

    public abstract int getNameCol();

    public abstract void setBrandCol(int brandCol);

    public abstract int getBrandCol();

    public abstract void readCustomers(CustomerContainer container) throws IOException;
    public abstract void readCustomers(CustomerContainer container, HashSet<String> payedByHK, String expectedBrand) throws IOException;

    public void setStartRow(int startRow) {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    public int getStartRow() {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    @Override
    public String toString() {
        // Intentionally no space after colon
        return "ProvisionFile:" + name;
    }

    public enum Type {
        EXPECTED,
        ACTUAL
    }

    public abstract String[][] showFile(int tableID) throws IOException;

    public int tableCount() throws IOException {
        throw new UnsupportedOperationException("Not supported for this filetype");
    }

    public static String[] uniqueInCol(ProvisionFile provisionFile, int col) throws IOException {
        String[][] fileRead = null;
        try {
            fileRead = provisionFile.showFile(provisionFile.getTableID());
        } catch (UnsupportedOperationException ignore) {
            fileRead = provisionFile.showFile(0);
        }
        HashSet<String> unique = new HashSet<>();

        for (String[] row : fileRead) {
            unique.add(row[col]);
        }

        return unique.toArray(new String[0]);
    }
}
