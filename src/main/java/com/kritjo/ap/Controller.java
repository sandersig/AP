package com.kritjo.ap;

import com.kritjo.ap.model.Customer;
import com.kritjo.ap.model.CustomerContainer;
import com.kritjo.ap.model.ProvisionFile;
import com.kritjo.ap.view.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class Controller {
    private MainWindow mainWindow;
    private MainContent mainContent;
    private final Stack<JComponent> temp = new Stack<>();

    private CustomerContainer customerContainer;
    private ArrayList<ProvisionFile> expected;
    private ArrayList<ProvisionFile> actual;

    public void addFile(ProvisionFile fileFromProfile, ProvisionFile.Type type) {
        if (type == ProvisionFile.Type.EXPECTED) expected.add(fileFromProfile);
        else actual.add(fileFromProfile);
    }

    public void initGUI() {
        mainContent = new MainContent(this);
        mainContent.initGUI();
        mainWindow = new MainWindow(mainContent);
    }

    public void startAP() {
        customerContainer = new CustomerContainer();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        APPanel apPanel = new APPanel(this);
        apPanel.initGUI();
        temp.add(apPanel);
        mainContent.ap(apPanel);
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

    public void apFilesReady() throws IOException {
        temp.pop().setVisible(false);
        APOptionsPanel apOptionsPanel = new APOptionsPanel(this);
        mainContent.apOptionsPanel(apOptionsPanel);
        temp.add(apOptionsPanel);
    }

    public String[] getBrands() throws IOException {
        ArrayList<String> brands = new ArrayList<>();

        for (ProvisionFile p : expected) {
            String[] uniqueInCol = ProvisionFile.uniqueInCol(p, p.getBrandCol());
            brands.addAll(Arrays.asList(uniqueInCol));
        }
        return brands.toArray(new String[0]);
    }

    public String[] getCodes() throws IOException {
        ArrayList<String> codes = new ArrayList<>();

        for (ProvisionFile p : expected) {
            String[] uniqueInCol = ProvisionFile.uniqueInCol(p, p.getProductCol());
            codes.addAll(Arrays.asList(uniqueInCol));
        }
        return codes.toArray(new String[0]);
    }

    public void startAPAfterOptions(String brand, HashSet<String> hkCodesHash) throws IOException {
        for (ProvisionFile p : expected) {
            p.readCustomers(customerContainer, hkCodesHash, brand);
        }
        for (ProvisionFile p : actual) {
            p.readCustomers(customerContainer);
        }

        ArrayList<Customer> deviations = customerContainer.getDeviations();
    }
}
