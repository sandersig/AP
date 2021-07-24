package com.kritjo.ap;

import javax.swing.*;
import java.io.File;

public class ProfileFrame extends JFrame {
    private Controller controller;
    private File[] existingProfiles;

    public ProfileFrame(Controller controller) {
        this.controller = controller;
    }

    public void initGUI() {
        existingProfiles = controller.existingProfiles();
        // List profiles
        // Click to edit
        // Add new profiles
        // Done button
    }
}
