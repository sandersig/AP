package com.kritjo.ap;

import java.io.File;

public class Controller {
    private MainWindow mainWindow;

    public void initGUI() {
        mainWindow = new MainWindow(this);
        mainWindow.initGUI();
    }

    public void startAP() {
        APFrame apFrame = new APFrame(this);
        apFrame.initGUI();
    }

    public void profileManager() {
        ProfileFrame profileFrame = new ProfileFrame(this);
        profileFrame.initGUI();
    }

    public File[] existingProfiles() {
        return ProvisionFile.existingProfiles();
    }
}
