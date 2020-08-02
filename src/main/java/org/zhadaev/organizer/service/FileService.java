package org.zhadaev.organizer.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class FileService {

    //сохранение файла
    public File save(final Map<String, String> map) {

        File file;
        try {
            file = File.createTempFile(map.get("task") + " " + new Date().getTime(), ".txt");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder text = new StringBuilder();
        switch (map.get("task")) {
            case "Comparator":
                text.append("task=").append(map.get("task")).append("\n");
                text.append("array1=").append(map.get("array1")).append("\n");
                text.append("array2=").append(map.get("array2"));
                break;
            case "Expander":
                text.append("task=").append(map.get("task")).append("\n");
                text.append("number=").append(map.get("number"));
                break;
            default:
                return null;
        }

        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(text.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    //открытие файла
    public Map<String, String> open(final File file) {

        Map<String, String> map = new HashMap<>(); //таблица для дальнейшего вывода результата

        FileReader fr;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            map.put("message", "Файл не найден"); //запись ошибки в таблицу
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
            map.put("message", "Неверная структура файла");
        }

        return map;

    }

}