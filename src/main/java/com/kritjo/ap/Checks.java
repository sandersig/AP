package com.kritjo.ap;

public abstract class Checks {

    public static boolean onlyNumbers(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
