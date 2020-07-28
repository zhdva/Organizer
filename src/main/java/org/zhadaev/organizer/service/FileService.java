package org.zhadaev.organizer.service;

import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileService {

    private static String task;
    private static String text;

    //сохранение файла для задачи Expander
    public static String save(final String taskName, final String number) {
        task = taskName;
        text = "task=" + task + "\n" + "number=" + number;
        return save();
    }

    //сохранение файла для задачи Comparator
    public static String save(final String taskName, final String a1, final String a2) {
        task = taskName;
        text = "task=" + task + "\n" +
                "array1=" + a1 + "\n" +
                "array2=" + a2;
        return save();
    }

    //сохранение файла
    private static String save() {
        FileWriter writer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        String date = sdf.format(new Date());
        String filePath = getPath() + task + " " + date + ".txt";
        try {
            writer = new FileWriter(filePath, false);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    //открытие файла
    public static Map<String, String> open(final File file) {

        Map<String, String> map = new HashMap<>(); //таблица для дальнейшего вывода результата
        final String exception = "Exception";

        FileReader fr;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            map.put(exception, "Файл не найден"); //запись ошибки в таблицу
            return map; //возврат таблицы с дальнейшим выводом ошибки
        }

        Scanner scan = new Scanner(fr);

        //парсинг файла
        String line;
        int index;
        String key;
        String value;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            index = line.indexOf("=");
            if (index != -1) {
                key = line.substring(0, index);
                value = line.substring(index + 1);
                map.put(key, value);
            }
        }

        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!map.containsKey("task") || !(map.containsKey("number") || map.containsKey("array1") && map.containsKey("array2"))) {
            map.put(exception, "Неверная структура файла");
        }

        return map;

    }

    //определение директории файла с программой
    protected static String getPath() {
        File file = null;
        try {
            file = new File(FileService.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert file != null;
        String path = file.getPath();
        String fileName = file.getName();
        return path.substring(0, path.length() - fileName.length());
    }

}
