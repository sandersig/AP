package com.kritjo.ap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel {
    private Controller controller;

    public StartMenu(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        JButton profilManager = new JButton("Profiladministrator");
        profilManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                controller.profileManager();
            }
        });

        add(profilManager);

        JButton startAP = new JButton("Start provisionskontroll");
        startAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                controller.startAP();
            }
        });

        add(startAP);
        setVisible(true);
    }
}
