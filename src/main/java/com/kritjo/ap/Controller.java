package com.kritjo.ap;

import com.kritjo.ap.model.ProvisionFile;
import com.kritjo.ap.view.APPanel;
import com.kritjo.ap.view.MainWindow;
import com.kritjo.ap.view.ProfileButton;
import com.kritjo.ap.view.ProfilePanel;

import javax.swing.*;
import java.io.File;

public class Controller {
    private MainWindow mainWindow;
    private JComponent temp;

    public void initGUI() {
        mainWindow = new MainWindow(this);
        mainWindow.initGUI();
    }

    public void startAP() {
        APPanel apPanel = new APPanel(this);
        apPanel.initGUI();
    }

    public void profileManager() {
        ProfilePanel profilePanel = new ProfilePanel(this);
        profilePanel.initGUI();
        mainWindow.profileManager(profilePanel);
    }

    public File[] existingProfiles() {
        return ProvisionFile.existingProfiles();
    }

    public void profileButtonPressed(ProfileButton profileButton) {
        if (temp != null) temp.setVisible(false);
        temp = mainWindow.profileManagerOptions(profileButton.getProfile());
    }

    public void newProfile() {
    }

    public void goToMainMenu() {
    }
}
