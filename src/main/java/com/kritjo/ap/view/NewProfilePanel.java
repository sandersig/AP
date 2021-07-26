package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;
import com.kritjo.ap.model.*;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class NewProfilePanel extends JPanel {
    private final Controller controller;
    private final GridBagConstraints c = new GridBagConstraints();

    public NewProfilePanel(Controller controller) {
        this.controller = controller;
        setBorder(BorderFactory.createMatteBorder(
                1, 5, 1, 1, Color.red));
        c.weightx = 10;
        c.weighty = 10;
    }

    public void initGUI() {
        setLayout(new GridBagLayout());
        c.ipady = 15;
        c.ipadx = 15;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;

        JLabel tittel = new JLabel("Ny profil");
        tittel.setFont(Main.H2);
        add(tittel, c);

        JComponent[] firstStep = new JComponent[6];
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        JLabel profileNameInfo = new JLabel("Profilnavn");
        profileNameInfo.setFont(Main.DEFAULTFONT);
        add(profileNameInfo, c);
        firstStep[0] = profileNameInfo;

        c.gridx = 1;
        JTextField profileName = new JTextField();
        profileName.setPreferredSize(new Dimension(200,25));
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

            if(status == JFileChooser.APPROVE_OPTION) {
                newProfile(profileName.getText(), fileDial.getSelectedFile(), (ProvisionFile.Type) type.getSelectedItem());
                for (JComponent jc: firstStep) {
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
        JTable table = new JTable(provisionFile.showFile(), new String[]{"A", "B", "C", "D", "E", "F", "G"});
        table.setFont(Main.DEFAULTFONT);

        JScrollPane container = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(container, c);
    }

}
