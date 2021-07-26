package com.kritjo.ap.view;

import com.kritjo.ap.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MainWindow extends JFrame {
    public MainWindow(MainContent mainContent) {
        super(Main.TITLE);
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainContent);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocale(new Locale("no"));
        initGUI();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setVisible(true);
    }
}
