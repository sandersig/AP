package com.kritjo.ap.view;

import com.kritjo.ap.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ProfilePanel extends JPanel {
    private final Controller controller;
    private final GridBagConstraints c = new GridBagConstraints();
    private File[] existingProfiles;

    public ProfilePanel(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        existingProfiles = controller.existingProfiles();

        setLayout(new GridBagLayout());
        c.ipadx = 15;
        c.ipady = 15;

        if (existingProfiles.length != 0) c.gridy = existingProfiles.length - 1;

        JButton newProfile = new JButton("Ny profil");
        newProfile.addActionListener(actionEvent -> {
            controller.newProfile();
        });
        add(newProfile, c);

        if (existingProfiles.length != 0) c.gridy = existingProfiles.length;
        else c.gridy = 1;

        JButton doneProfile = new JButton("Ferdig");
        doneProfile.addActionListener(actionEvent -> {
            controller.goToMainMenu();
        });
        add(doneProfile, c);

        c.gridy = 0;
        c.gridx = 1;
        JLabel profileButtonInfo = new JLabel("Trykk på en eksisterende profil for å endre");
        add(profileButtonInfo, c);

        for (int i = 0; i < existingProfiles.length; i++) {
            c.gridy = i+1;
            ProfileButton profileButton = new ProfileButton(controller, existingProfiles[i]);
            add(profileButton, c);
        }

        setVisible(true);
    }
}
