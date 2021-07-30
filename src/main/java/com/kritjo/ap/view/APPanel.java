package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;
import com.kritjo.ap.model.ProvisionFile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class APPanel extends JPanel {
    private final Controller controller;
    private final GridBagConstraints c = new GridBagConstraints();

    public APPanel(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        setLayout(new GridBagLayout());
        c.ipadx = 15;
        c.ipady = 15;

        c.gridy = 0;
        c.gridx = 0;
        JLabel expectedInfo = new JLabel("Forventet provisjonsfiler");
        expectedInfo.setFont(Main.DEFAULTFONT);
        add(expectedInfo, c);

        c.gridy = 1;
        FilePanel expectedPanel = new FilePanel(controller, ProvisionFile.Type.EXPECTED);
        add(expectedPanel, c);

        c.gridy = 0;
        c.gridx = 1;
        JLabel actualInfo = new JLabel("Faktiske provisjonsfiler");
        actualInfo.setFont(Main.DEFAULTFONT);
        add(actualInfo, c);

        c.gridy = 1;
        FilePanel actualPanel = new FilePanel(controller, ProvisionFile.Type.ACTUAL);
        add(actualPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        JButton continueButton = new JButton("Fortsett");
        continueButton.setFont(Main.DEFAULTFONT);
        continueButton.addActionListener(actionEvent -> {
            try {
                controller.apFilesReady();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(continueButton, c);
        // Choose source files to add using profile, or add a new profile. New profile can optionally be saved.
        // When all profiles are added, hit start and get the result of the matching.
    }
}
