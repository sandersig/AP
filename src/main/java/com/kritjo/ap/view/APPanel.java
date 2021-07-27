package com.kritjo.ap.view;

import com.kritjo.ap.Controller;

import javax.swing.*;

public class APPanel extends JPanel {
    private final Controller controller;

    public APPanel(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        // Choose source files to add using profile, or add a new profile. New profile can optionally be saved.
        // When all profiles are added, hit start and get the result of the matching.
    }
}
