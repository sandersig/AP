package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;

public class StartMenu extends JPanel {
    private final Controller controller;

    public StartMenu(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        JButton profilManager = new JButton("Profiladministrator");
        profilManager.setFont(Main.DEFAULTFONT);
        profilManager.addActionListener(actionEvent -> {
            setVisible(false);
            controller.profileManager();
        });

        add(profilManager);

        JButton startAP = new JButton("Start provisionskontroll");
        startAP.setFont(Main.DEFAULTFONT);
        startAP.addActionListener(actionEvent -> {
            setVisible(false);
            controller.startAP();
        });

        add(startAP);
        setVisible(true);
    }
}
