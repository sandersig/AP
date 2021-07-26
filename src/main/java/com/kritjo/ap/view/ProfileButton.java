package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;
import java.io.File;

public class ProfileButton extends JButton {
    private final File profile;

    public ProfileButton(Controller controller, File profile) {
        this.profile = profile;
        setFont(Main.DEFAULTFONT);
        String name = profile.getName().substring(0, profile.getName().length() - 4);
        setText(name);
        addActionListener(actionEvent -> controller.profileButtonPressed(this));
    }

    public File getProfile() {
        return profile;
    }
}
