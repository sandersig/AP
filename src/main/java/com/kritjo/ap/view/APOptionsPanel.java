package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;

public class APOptionsPanel extends JPanel {
    public APOptionsPanel(Controller controller) throws IOException {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = c.ipady = 15;

        JLabel brandInfo = new JLabel("Hvilket merke gjør du provkontroll for?");
        brandInfo.setFont(Main.DEFAULTFONT);
        add(brandInfo, c);
        JComboBox<String> brand = new JComboBox<>(controller.getBrands());
        brand.setFont(Main.DEFAULTFONT);
        c.gridy = 1;
        add(brand, c);

        JLabel HKCodesInfo = new JLabel("Velg koder som skal betales av HK");
        HKCodesInfo.setFont(Main.DEFAULTFONT);
        c.gridy = 2;
        add(HKCodesInfo, c);

        JList<String> HKCodes = new JList<>(controller.getCodes());
        HKCodes.setFont(Main.DEFAULTFONT);
        HKCodes.setLayoutOrientation(JList.VERTICAL);
        HKCodes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        c.gridy = 3;
        add(HKCodes, c);

        JButton start = new JButton("Start AP");
        start.addActionListener(actionEvent -> {
            HashSet<String> HKCodesHash = new HashSet<>(HKCodes.getSelectedValuesList());
            try {
                controller.startAPAfterOptions((String) brand.getSelectedItem(), HKCodesHash);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        c.gridy = 4;
        add(start, c);
    }
}
