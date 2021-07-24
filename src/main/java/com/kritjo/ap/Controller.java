package com.kritjo.ap;

import java.io.File;

public class Controller {

    public void initGUI() {
        MainWindow mainWindow = new MainWindow(this);
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
