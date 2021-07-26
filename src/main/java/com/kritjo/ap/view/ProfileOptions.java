package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;
import java.io.File;

public class ProfileOptions extends JPanel {
    private Controller controller;
    private File profile;

    public ProfileOptions(Controller controller, File profile) {
        this.controller = controller;
        this.profile = profile;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton delete = new JButton("Slett " + profile.getName().substring(0, profile.getName().length() - 4));
        delete.setFont(Main.DEFAULTFONT);
        delete.addActionListener(actionEvent -> {
            profile.delete();
            setVisible(false);
        });
        add(delete);

        setVisible(true);
    }
}
