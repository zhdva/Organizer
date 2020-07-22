package org.zhadaev.organizer;

public class Expander {

    public static String expand(final String num) {

        StringBuilder result = new StringBuilder();
        Character zero = '0';

        for (int i = 0; i < num.length(); i++) {
            if (!zero.equals(num.charAt(i))) {
                int n = Character.digit(num.charAt(i), 10) * (int)Math.pow(10, num.length() - i - 1);
                if (result.length() != 0) result.append(" + ");
                result.append(n);
            }
        }

        return result.toString();

    }

}