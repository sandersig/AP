package com.kritjo.ap;

import javax.swing.*;
import java.io.File;

public class ProfileButton extends JButton {
    private final Controller controller;
    private final File profile;
    private final String name;

    public ProfileButton(Controller controller, File profile) {
        this.profile = profile;
        this.controller = controller;
        name = profile.getName().substring(0, profile.getName().length() - 4);
        setText(name);
        addActionListener(actionEvent -> {
            controller.profileButtonPressed(this);
        });
    }

}
