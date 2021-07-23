package com.kritjo.ap;

/**
 * Helper class providing methods that can be used universally.
 */
public abstract class Checks {

    /**
     * @return true if string contains only numbers, else return false.
     */
    public static boolean onlyNumbers(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
