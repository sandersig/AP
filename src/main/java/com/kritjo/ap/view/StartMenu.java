package com.kritjo.ap.view;

import com.kritjo.ap.Controller;

import javax.swing.*;

public class StartMenu extends JPanel {
    private final Controller controller;

    public StartMenu(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        JButton profilManager = new JButton("Profiladministrator");
        profilManager.addActionListener(actionEvent -> {
            setVisible(false);
            controller.profileManager();
        });

        add(profilManager);

        JButton startAP = new JButton("Start provisionskontroll");
        startAP.addActionListener(actionEvent -> {
            setVisible(false);
            controller.startAP();
        });

        add(startAP);
        setVisible(true);
    }
}
