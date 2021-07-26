package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainContent extends JPanel {
    private final Controller controller;
    private final GridBagConstraints c = new GridBagConstraints();

    public MainContent(Controller controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
    }

    public void initGUI() {
        c.ipadx = 15;
        c.ipady = 15;

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;

        JLabel start = new JLabel("AP - Automatisk provisjonskontroll");
        start.setFont(Main.H1);
        add(start, c);

        startMenu();

        setVisible(true);
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
    }

    public void profileManager(ProfilePanel profileManager) {
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 1;
        add(profileManager, c);
    }

    public JComponent profileManagerOptions(File profile) {
        c.gridy = 0;
        c.gridx = 2;
        ProfileOptions profileOptions = new ProfileOptions(controller, profile);
        add(profileOptions, c);
        return profileOptions;
    }

    public void startMenu() {
        c.gridy = 1;
        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        StartMenu startMenu = new StartMenu(controller);
        startMenu.initGUI();
        add(startMenu, c);
    }

    public void newProfilePanel(NewProfilePanel newProfilePanel) {
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 1;
        c.gridx = 1;
        add(newProfilePanel, c);
    }
}
