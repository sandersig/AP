package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;
import com.kritjo.ap.model.ProvisionFile;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class NewProfilePanel extends JPanel {
    private final Controller controller;

    public NewProfilePanel(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 15;
        c.ipadx = 15;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;

        JLabel tittel = new JLabel("Ny profil");
        tittel.setFont(Main.H2);
        add(tittel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        JLabel profileNameInfo = new JLabel("Profilnavn");
        profileNameInfo.setFont(Main.DEFAULTFONT);
        add(profileNameInfo, c);

        c.gridx = 1;
        JTextField profileName = new JTextField();
        profileName.setPreferredSize(new Dimension(200,25));
        profileName.setFont(Main.DEFAULTFONT);
        add(profileName, c);

        c.gridx = 0;
        c.gridy = 2;
        JLabel typeInfo = new JLabel("Type profil");
        profileNameInfo.setFont(Main.DEFAULTFONT);
        add(typeInfo, c);

        c.gridx = 1;
        JComboBox<ProvisionFile.Type> type = new JComboBox<>(ProvisionFile.Type.values());
        type.setFont(Main.DEFAULTFONT);
        add(type, c);

        c.gridx = 0;
        c.gridy = 3;
        JLabel kildefilInfo = new JLabel("Kildefil");
        kildefilInfo.setFont(Main.DEFAULTFONT);
        add(kildefilInfo, c);

        c.gridx = 1;
        JFileChooser kildefil = new JFileChooser();
        kildefil.setApproveButtonText("Fortsett");

        add(kildefil, c);


    }
}
