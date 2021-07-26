package com.kritjo.ap;

import com.kritjo.ap.model.ProvisionFile;
import com.kritjo.ap.view.*;

import javax.swing.*;
import java.io.File;
import java.util.Stack;

public class Controller {
    private MainWindow mainWindow;
    private MainContent mainContent;
    private Stack<JComponent> temp = new Stack<>();

    public void initGUI() {
        mainContent = new MainContent(this);
        mainContent.initGUI();
        mainWindow = new MainWindow(mainContent, this);
    }

    public void startAP() {
        APPanel apPanel = new APPanel(this);
        apPanel.initGUI();
    }

    public void profileManager() {
        ProfilePanel profilePanel = new ProfilePanel(this);
        profilePanel.initGUI();
        temp.add(profilePanel);
        mainContent.profileManager(profilePanel);
    }

    public File[] existingProfiles() {
        return ProvisionFile.existingProfiles();
    }

    public void profileButtonPressed(ProfileButton profileButton) {
        if (temp.peek().getClass().equals(ProfileOptions.class)) temp.pop().setVisible(false);
        temp.add(mainContent.profileManagerOptions(profileButton.getProfile()));
    }

    public void newProfile() {
        while (!temp.isEmpty() && (temp.peek().getClass().equals(ProfilePanel.class) ||
                temp.peek().getClass().equals(ProfileOptions.class))) temp.pop().setVisible(false);

        NewProfilePanel newProfilePanel = new NewProfilePanel(this);
        newProfilePanel.initGUI();
        mainContent.newProfilePanel(newProfilePanel);
        temp.add(newProfilePanel);
    }

    public void goToMainMenu() {
        for (JComponent jComponent : temp) {
            jComponent.setVisible(false);
        }
        temp.clear();
        mainContent.startMenu();
    }

    public void back() {
        temp.pop().setVisible(false);
    }

    public void pack() {
        mainWindow.pack();
    }
}
