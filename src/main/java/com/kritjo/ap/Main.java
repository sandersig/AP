package com.kritjo.ap;

import java.awt.*;

/**
 * Dummy.
 */
public class Main {
    public final static String TITLE = "AP";

    public final static Font DEFAULTFONT = new Font("TimesRoman", Font.PLAIN, 16);
    public final static Font H1 = new Font("TimesRoman", Font.BOLD, 26);
    public final static Font H2 = new Font("TimesRoman", Font.PLAIN, 20);

    private static Controller controller = new Controller();

    public static void main(String[] args) {
        ProvisionFile.existingProfiles();
        controller.initGUI();
    }
}
