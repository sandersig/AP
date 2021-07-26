package com.kritjo.ap;

import com.kritjo.ap.model.ProvisionFile;
import com.kritjo.ap.view.*;

import javax.swing.*;
import java.io.File;
import java.util.Stack;

public class Controller {
    private MainWindow mainWindow;
    private Stack<JComponent> temp = new Stack<>();

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
        temp.add(profilePanel);
        mainWindow.profileManager(profilePanel);
    }

    public File[] existingProfiles() {
        return ProvisionFile.existingProfiles();
    }

    public void profileButtonPressed(ProfileButton profileButton) {
        if (temp.peek().getClass().equals(ProfileOptions.class)) temp.pop().setVisible(false);
        temp.add(mainWindow.profileManagerOptions(profileButton.getProfile()));
    }

    public void newProfile() {
        if (temp.peek().getClass().equals(ProfilePanel.class)) temp.pop().setVisible(false);
        NewProfilePanel newProfilePanel = new NewProfilePanel(this);
        newProfilePanel.initGUI();
        mainWindow.newProfilePanel(newProfilePanel);
        temp.add(newProfilePanel);
    }

    public void goToMainMenu() {
        for (JComponent jComponent : temp) {
            jComponent.setVisible(false);
        }
        temp.clear();
        mainWindow.startMenu();
    }
}
