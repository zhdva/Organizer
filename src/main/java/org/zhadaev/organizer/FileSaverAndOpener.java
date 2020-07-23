package org.zhadaev.organizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

public class FileSaverAndOpener {

    private static String task;
    private static String text;

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
        FileWriter writer = null;
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

}
