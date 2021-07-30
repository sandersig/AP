package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;
import com.kritjo.ap.model.*;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NewProfilePanel extends JPanel {
    private final Controller controller;
    private final GridBagConstraints c = new GridBagConstraints();

    public NewProfilePanel(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        setLayout(new GridBagLayout());
        c.ipady = 15;
        c.ipadx = 15;

        c.gridx = 0;
        c.gridy = 0;

        JLabel tittel = new JLabel("Ny profil");
        tittel.setFont(Main.H2);
        add(tittel, c);

        JComponent[] firstStep = new JComponent[6];
        c.gridx = 0;
        c.gridy = 1;

        JLabel profileNameInfo = new JLabel("Profilnavn");
        profileNameInfo.setFont(Main.DEFAULTFONT);
        add(profileNameInfo, c);
        firstStep[0] = profileNameInfo;

        c.gridx = 1;
        JTextField profileName = new JTextField();
        profileName.setPreferredSize(new Dimension(200, 25));
        profileName.setFont(Main.DEFAULTFONT);
        add(profileName, c);
        firstStep[1] = profileName;

        c.gridx = 0;
        c.gridy = 2;
        JLabel typeInfo = new JLabel("Type profil");
        profileNameInfo.setFont(Main.DEFAULTFONT);
        add(typeInfo, c);
        firstStep[2] = typeInfo;

        c.gridx = 1;
        JComboBox<ProvisionFile.Type> type = new JComboBox<>(ProvisionFile.Type.values());
        type.setFont(Main.DEFAULTFONT);
        add(type, c);
        firstStep[3] = type;

        c.gridx = 0;
        c.gridy = 3;
        JLabel kildefilInfo = new JLabel("Kildefil");
        kildefilInfo.setFont(Main.DEFAULTFONT);
        add(kildefilInfo, c);
        firstStep[4] = kildefilInfo;

        c.gridx = 1;
        JButton kildefil = new JButton("Åpne fil");
        kildefil.setFont(Main.DEFAULTFONT);


        kildefil.addActionListener(actionEvent -> {
            JFileChooser fileDial = new JFileChooser();
            fileDial.setApproveButtonText("Fortsett");
            FileNameExtensionFilter acceptedTypes = new FileNameExtensionFilter("Accepted types (csv, xls, xlsx, html, pdf)", "csv", "xls", "xlsx", "html", "pdf");
            fileDial.setFileFilter(acceptedTypes);
            int status = fileDial.showOpenDialog(this);

            if (status == JFileChooser.APPROVE_OPTION) {
                newProfile(profileName.getText(), fileDial.getSelectedFile(), (ProvisionFile.Type) type.getSelectedItem());
                for (JComponent jc : firstStep) {
                    jc.setVisible(false);
                }
            }
        });
        add(kildefil, c);
        firstStep[5] = kildefil;

    }

    public void newProfile(String name, File selectedFile, ProvisionFile.Type type) {
        ProvisionFile provisionFile;
        switch (FilenameUtils.getExtension(selectedFile.getAbsolutePath())) {
            case ("csv") -> provisionFile = new CsvFile(selectedFile, name, type);
            case ("xls"), ("xlsx") -> provisionFile = new ExcelFile(selectedFile, name, type);
            case ("pdf") -> provisionFile = new PdfFile(selectedFile, name, type);
            case ("html") -> provisionFile = new HtmlFile(selectedFile, name, type);
            default -> throw new IllegalStateException("Unexpected filetype: " + FilenameUtils.getExtension(selectedFile.getAbsolutePath()));
        }
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 5;
        JLabel colorInfo = new JLabel("Velg en kolonne for hver av følgende egenskaper:");
        colorInfo.setFont(Main.DEFAULTFONT);
        add(colorInfo, c);
        c.gridwidth = 1;

        String[][] fileRead = provisionFile.showFile();
        HashMap<String, Integer> headers = new HashMap<>();

        char ch = 'A';
        for (int i = 0; i < fileRead[0].length; i++) {
            headers.put(String.valueOf(ch), i);
            ch++;
        }

        c.weightx = 0.5;
        c.gridy = 2;
        c.gridx = 0;
        JLabel gsmColInfo = new JLabel("Telefonnummer");
        gsmColInfo.setFont(Main.DEFAULTFONT);
        add(gsmColInfo, c);
        c.gridy = 3;
        c.gridx = 0;
        JComboBox<String> gsmCol = new JComboBox<>(headers.keySet().toArray(new String[0]));
        gsmCol.setFont(Main.DEFAULTFONT);
        add(gsmCol, c);

        c.gridy = 2;
        c.gridx = 1;
        JLabel provColInfo = new JLabel("Provision");
        provColInfo.setFont(Main.DEFAULTFONT);
        add(provColInfo, c);
        c.gridy = 3;
        c.gridx = 1;
        JComboBox<String> provCol = new JComboBox<>(headers.keySet().toArray(new String[0]));
        provCol.setFont(Main.DEFAULTFONT);
        add(provCol, c);

        c.gridy = 2;
        c.gridx = 2;
        JLabel refColInfo = new JLabel("Referanse");
        refColInfo.setFont(Main.DEFAULTFONT);
        add(refColInfo, c);
        c.gridy = 3;
        c.gridx = 2;
        JComboBox<String> refCol = new JComboBox<>(headers.keySet().toArray(new String[0]));
        refCol.setFont(Main.DEFAULTFONT);
        add(refCol, c);

        c.gridy = 2;
        c.gridx = 3;
        JLabel nameColInfo = new JLabel("Kundens navn");
        nameColInfo.setFont(Main.DEFAULTFONT);
        add(nameColInfo, c);
        c.gridy = 3;
        c.gridx = 3;
        JComboBox<String> nameCol = new JComboBox<>(headers.keySet().toArray(new String[0]));
        nameCol.setFont(Main.DEFAULTFONT);
        add(nameCol, c);

        c.gridy = 2;
        c.gridx = 4;
        JLabel productColInfo = new JLabel("Produkt/Årsak");
        productColInfo.setFont(Main.DEFAULTFONT);
        add(productColInfo, c);
        c.gridy = 3;
        c.gridx = 4;
        JComboBox<String> productCol = new JComboBox<>(headers.keySet().toArray(new String[0]));
        productCol.setFont(Main.DEFAULTFONT);
        add(productCol, c);

        JTable table = new JTable(fileRead, headers.keySet().toArray(new String[0]));
        table.setFont(Main.DEFAULTFONT);

        JScrollPane container = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 5;
        c.gridwidth = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(container, c);

        JLabel rowInfo = new JLabel("Hvilken rad inneholder første datapunkt(er)?");
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        add(rowInfo, c);

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(1, 0, fileRead.length, 1);
        JSpinner row = new JSpinner(spinnerNumberModel);
        c.gridx = 1;
        add(row, c);

        JButton continueButton = new JButton("Fortsett");
        continueButton.addActionListener(actionEvent -> {
            provisionFile.setProvisionCol(headers.get((String) provCol.getSelectedItem()));
            provisionFile.setProductCol(headers.get((String) productCol.getSelectedItem()));
            provisionFile.setGsmNrCol(headers.get((String) gsmCol.getSelectedItem()));
            provisionFile.setNameCol(headers.get((String) nameCol.getSelectedItem()));
            provisionFile.setRefCol(headers.get((String) refCol.getSelectedItem()));
            provisionFile.setStartRow((Integer) row.getValue());
            try {
                provisionFile.saveProfile(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            controller.goToMainMenu();
        });
        continueButton.setFont(Main.DEFAULTFONT);
        c.gridx = 4;
        add(continueButton, c);
    }

}
