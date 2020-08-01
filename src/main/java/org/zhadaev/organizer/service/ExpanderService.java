package org.zhadaev.organizer.service;

import org.springframework.stereotype.Service;

@Service
public class ExpanderService {

    public String expand(final String num) {

        StringBuilder result = new StringBuilder();
        Character zero = '0';

        for (int i = 0; i < num.length(); i++) {
            if (!zero.equals(num.charAt(i))) {
                if (result.length() != 0) result.append(" + ");
                result.append(num.charAt(i));
                for (int j = 1; j < num.length() - i; j++) {
                    result.append(zero);
                }
            }
        }

        return result.toString();

    }

}