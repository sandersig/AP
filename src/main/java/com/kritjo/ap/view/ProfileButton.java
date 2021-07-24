package com.kritjo.ap.view;

import com.kritjo.ap.Controller;

import javax.swing.*;
import java.io.File;

public class ProfileButton extends JButton {
    private final File profile;

    public ProfileButton(Controller controller, File profile) {
        this.profile = profile;
        String name = profile.getName().substring(0, profile.getName().length() - 4);
        setText(name);
        addActionListener(actionEvent -> {
            controller.profileButtonPressed(this);
        });
    }

    public File getProfile() {
        return profile;
    }
}
