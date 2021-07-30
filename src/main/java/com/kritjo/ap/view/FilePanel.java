package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;
import com.kritjo.ap.model.ProvisionFile;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;

public class FilePanel extends JPanel {

    public FilePanel(Controller controller, ProvisionFile.Type type) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addFile = new JButton("Legg til fil");
        addFile.setFont(Main.DEFAULTFONT);
        addFile.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter acceptedTypes = new FileNameExtensionFilter("Accepted types (csv, xls, xlsx, html, pdf)", "csv", "xls", "xlsx", "html", "pdf");
            fileChooser.setFileFilter(acceptedTypes);

            int status = fileChooser.showOpenDialog(this);

            File file = null;
            if (status == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            }

            File[] existingProfiles = controller.existingProfiles();
            Object[] options = new String[existingProfiles.length];

            for (int i = 0; i < existingProfiles.length; i++) {
                options[i] = FilenameUtils.removeExtension(existingProfiles[i].getName());
            }

            String profileName = (String) JOptionPane.showInputDialog(this, "Velg profil", "Velg profil",
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            try {
                controller.addFile(ProvisionFile.getFileFromProfile(profileName, file, profileName), type);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JLabel addedProfile = new JLabel(profileName);
            addedProfile.setFont(Main.DEFAULTFONT);
            add(addedProfile);
            controller.pack();
        });
        add(addFile);
    }
}
