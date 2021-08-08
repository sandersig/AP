package com.kritjo.ap.view;

import com.kritjo.ap.Main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Made from http://www.devx.com/tips/Tip/5342
 */
public class CheckboxList extends JList<JCheckBox> {
    private int length;

    protected static Border noFocusBorder =
            new EmptyBorder(1, 1, 1, 1);

    public CheckboxList(String[] codes) {
        length = codes.length;
        setCellRenderer(new CellRenderer());

        addMouseListener(new MouseAdapter() {
                             @Override
                             public void mousePressed(MouseEvent e) {
                                 int index = locationToIndex(e.getPoint());

                                 if (index != -1) {
                                     JCheckBox checkbox = getModel().getElementAt(index);
                                     checkbox.setSelected(!checkbox.isSelected());
                                     repaint();
                                 }
                             }
                         }
        );

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JCheckBox[] boxes = new JCheckBox[codes.length];
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new JCheckBox(codes[i]);
            boxes[i].setFont(Main.DEFAULTFONT);
        }
        setListData(boxes);
    }

    protected class CellRenderer implements ListCellRenderer<JCheckBox> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JCheckBox> list, JCheckBox value, int index, boolean isSelected, boolean cellHasFocus) {
            value.setBackground(isSelected ?
                    getSelectionBackground() : getBackground());
            value.setForeground(isSelected ?
                    getSelectionForeground() : getForeground());
            value.setEnabled(isEnabled());
            value.setFont(getFont());
            value.setFocusPainted(false);
            value.setBorderPainted(true);
            value.setBorder(isSelected ?
                    UIManager.getBorder(
                            "List.focusCellHighlightBorder") : noFocusBorder);
            return value;
        }
    }

    public void setAllSelected() {
        setSelectionInterval(0, length - 1);
    }

    public void setAllDeselected() {
        setSelectionInterval(0,1);
    }
}
