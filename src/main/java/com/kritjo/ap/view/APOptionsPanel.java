package com.kritjo.ap.view;

import com.kritjo.ap.Controller;
import com.kritjo.ap.Main;
import com.kritjo.ap.model.ProvisionFile;

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

        CheckboxList HKCodes = new CheckboxList(controller.getCodes(ProvisionFile.Type.EXPECTED));
        HKCodes.setFont(Main.DEFAULTFONT);
        HKCodes.setLayoutOrientation(JList.VERTICAL);
        HKCodes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane containerHKCodes = new JScrollPane(HKCodes);
        containerHKCodes.setViewportView(HKCodes);
        c.gridy = 3;
        add(containerHKCodes, c);

        JLabel manualCodesActualInfo = new JLabel("Velg koder som skal holdes utenfor automatikken fra ACTUAL:");
        manualCodesActualInfo.setFont(Main.DEFAULTFONT);
        c.gridy = 2;
        c.gridx = 1;
        add(manualCodesActualInfo, c);

        CheckboxList manualCodesActual = new CheckboxList(controller.getCodes(ProvisionFile.Type.ACTUAL));
        manualCodesActual.setFont(Main.DEFAULTFONT);
        manualCodesActual.setLayoutOrientation(JList.VERTICAL);
        manualCodesActual.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane manualContainer = new JScrollPane(manualCodesActual);
        manualContainer.setViewportView(manualCodesActual);
        c.gridy = 3;
        add(manualContainer, c);

        JLabel manualCodesInfo = new JLabel("Velg koder som skal holdes utenfor automatikken fra EXCPECTED:");
        manualCodesInfo.setFont(Main.DEFAULTFONT);
        c.gridy = 2;
        c.gridx = 2;
        add(manualCodesInfo, c);

        CheckboxList manualCodes = new CheckboxList(controller.getCodes(ProvisionFile.Type.EXPECTED));
        manualCodes.setFont(Main.DEFAULTFONT);
        manualCodes.setLayoutOrientation(JList.VERTICAL);
        manualCodes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane actualContainer = new JScrollPane(manualCodes);
        actualContainer.setViewportView(manualCodes);
        c.gridy = 3;
        add(actualContainer, c);

        c.gridwidth = 3;
        JButton start = new JButton("Start AP");
        start.addActionListener(actionEvent -> {
            HashSet<String> HKCodesHash = new HashSet<>();
            HKCodes.setAllSelected();
            for (JCheckBox j : HKCodes.getSelectedValuesList()) {
                if (j.isSelected()) {
                    HKCodesHash.add(j.getText());
                }
            }
            HKCodes.setAllDeselected();
            HashSet<String> productManualActual = new HashSet<>();
            manualCodesActual.setAllSelected();
            for (JCheckBox j : manualCodesActual.getSelectedValuesList()) {
                if (j.isSelected()) {
                    productManualActual.add(j.getText());
                }
            }
            manualCodesActual.setAllDeselected();
            HashSet<String> productManualExcpected = new HashSet<>();
            manualCodes.setAllSelected();
            for (JCheckBox j : manualCodes.getSelectedValuesList()) {
                if (j.isSelected()) {
                    productManualExcpected.add(j.getText());
                }
            }
            manualCodes.setAllDeselected();
            try {
                controller.startAPAfterOptions((String) brand.getSelectedItem(), HKCodesHash, productManualActual, productManualExcpected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        c.gridy = 4;
        add(start, c);
    }
}
