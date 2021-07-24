package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Locale;

public class MainWindow extends JFrame {
    private GridBagConstraints c = new GridBagConstraints();
    private final Controller controller;

    public MainWindow(Controller controller) {
        super(Main.TITLE);
        this.controller = controller;

        setLocale(new Locale("no"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Center the window
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void profileManager(ProfilePanel profileManager) {
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 1;
        add(profileManager, c);
        pack();
    }

    public JComponent profileManagerOptions(File profile) {
        ProfileOptions profileOptions = new ProfileOptions(controller, profile);
        c.gridy = 1;
        c.gridx = 2;
        add(profileOptions, c);
        pack();
        return profileOptions;
    }

    public void startMenu() {
        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 1;

        StartMenu startMenu = new StartMenu(controller);
        startMenu.initGUI();
        add(startMenu, c);

        pack();
    }
}
