package com.kritjo.ap;

import javax.swing.*;

public class StartMenu extends JPanel {
    private Controller controller;

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
