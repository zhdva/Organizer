package org.zhadaev.organizer.service;

import java.util.*;

public class ComparatorService {

    public static String[] compare(final String[] a1, final String[] a2) {

        SortedSet<String> sortedStrings = new TreeSet();

        for (String a1Str: a1) {
            for (String a2Str: a2) {
                if (a2Str.contains(a1Str)) {
                    sortedStrings.add(a1Str);
                }
            }
        }

        return sortedStrings.toArray(new String[0]);

    }

}