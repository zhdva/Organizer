package org.zhadaev.organizer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

public class FileSaverAndOpener {

    private static String task;
    private static String text;

    public static String save(final String taskName, final String number) {
        task = taskName;
        text = "task=" + task + "\n" + "number=" + number;
        return save();
    }

    public static String save(final String taskName, final String a1, final String a2) {
        task = taskName;
        text = "task=" + task + "\n" +
                "array1=" + a1 + "\n" +
                "array2=" + a2;
        return save();
    }

    private static String save() {
        FileWriter writer;
        String filePath = getPath() + task + " " + new Date() + ".txt";
        try {
            writer = new FileWriter(filePath, false);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static Map<String, String> open(final File file) {

        Map<String, String> map = new HashMap<>();
        final String exception = "Exception";

        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fr == null) {
            map.put(exception, "Файл не найден");
            return map;
        }

        Scanner scan = new Scanner(fr);

        String line;
        int index;
        String key;
        String value;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            index = line.indexOf("=");
            key = line.substring(0, index);
            value = line.substring(index + 1);
            map.put(key, value);
        }

        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!map.containsKey("task") || !(map.containsKey("number") || map.containsKey("a1") && map.containsKey("a2"))) {
            map.put(exception, "Неверная структура файла");
        }

        return map;

    }

    private static String getPath() {
        File file = null;
        try {
            file = new File(FileSaverAndOpener.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String path = file.getPath();
        String fileName = file.getName();
        return path.substring(0, path.length() - fileName.length());
    }

}
