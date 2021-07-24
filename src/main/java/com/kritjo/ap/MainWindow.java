package com.kritjo.ap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 1;

        StartMenu startMenu = new StartMenu(controller);
        startMenu.initGUI();
        add(startMenu, c);

        pack();
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
}
