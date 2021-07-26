package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainContent extends JPanel {
    private final Controller controller;

    public MainContent(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {


        JLabel start = new JLabel("AP - Automatisk provisjonskontroll");
        start.setFont(Main.H1);
        add(start);

        startMenu();

        setVisible(true);
    }

    public void profileManager(ProfilePanel profileManager) {
        add(profileManager);
    }

    public JComponent profileManagerOptions(File profile) {
        Component[] arr = this.getComponents();
        ProfilePanel component = null;
        for (Component c : arr) {
            if (c.getClass().equals(ProfilePanel.class)) {
                component = (ProfilePanel) c;
            }
        }
        ProfileOptions profileOptions = new ProfileOptions(controller, profile);
        assert component != null;
        component.addOptions(profileOptions);
        return profileOptions;
    }

    public void startMenu() {
        StartMenu startMenu = new StartMenu(controller);
        startMenu.initGUI();
        add(startMenu);
    }

    public void newProfilePanel(NewProfilePanel newProfilePanel) {
        add(newProfilePanel);
    }
}
