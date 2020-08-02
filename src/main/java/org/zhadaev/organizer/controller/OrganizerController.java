package org.zhadaev.organizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.zhadaev.organizer.service.ComparatorService;
import org.zhadaev.organizer.service.ExpanderService;
import org.zhadaev.organizer.service.FileService;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("session")
public class OrganizerController {

    private ComparatorService comparatorService;
    private ExpanderService expanderService;
    private FileService fileService;
    private ServletContext servletContext;
    private Map<String, String> map; //карта для отображения информации на странице

    //инициализация сервисов
    @Autowired
    public void setServices(final ComparatorService comparatorService,
                            final ExpanderService expanderService,
                            final FileService fileService,
                            final ServletContext servletContext) {
        this.comparatorService = comparatorService;
        this.expanderService = expanderService;
        this.fileService = fileService;
        this.servletContext = servletContext;
    }

    public OrganizerController() {
        map = new HashMap<>();
        map.put("message", "Выберите задачу или загрузите файл"); //первое сообщение при загрузке
    }

    //загрузка страницы с приложением
    @GetMapping(value = "/")
    public ModelAndView organizer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Organizer");
        modelAndView.addObject("map", map);
        return modelAndView;
    }

    //вычисление результата
    @PostMapping(value = "/calculate")
    public ModelAndView calculate(@RequestBody MultiValueMap<String, String> form) {

        map = form.toSingleValueMap();

        map.put("message", calculate());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    //сохранение данных формы в файл
    @PostMapping("/save")
    public ResponseEntity<InputStreamResource> saveFile(@RequestBody MultiValueMap<String, String> form) {

        File file = fileService.save(form.toSingleValueMap());

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String mineType = servletContext.getMimeType(file.getName());
        MediaType mediaType = MediaType.parseMediaType(mineType);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    //загрузка файла на сервер
    @PostMapping(value = "/open")
    public ModelAndView openFile(@RequestParam("file") MultipartFile mpFile) {

        File file = null;
        try {
            file = File.createTempFile(String.valueOf(new Date().getTime()), ".txt");
            mpFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        map = fileService.open(file); //считывание информации из файла

        if (!map.containsKey("message")) {
            map.put("message", calculate()); //запись результата в карту
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    //метод для выполнения расчёта
    private String calculate() {

        String result;
        switch (map.get("task")) {
            case "Comparator":
                String[] a1 = map.get("array1").split(" ");
                String[] a2 = map.get("array2").split(" ");
                String[] array = comparatorService.compare(a1, a2);
                String tmp1 = Arrays.toString(array).replace(",", " ");
                tmp1 = tmp1.substring(1, tmp1.length() - 1);
                if (!tmp1.equals("")) {
                    result = tmp1;
                } else {
                    result = "Совпадения не найдены";
                }
                break;
            case "Expander":
                String tmp2 = expanderService.expand(map.get("number"));
                if (!tmp2.equals("")) {
                    result = tmp2;
                } else {
                    result = "Введите число";
                }
                break;
            default:
                result = "Выберите задачу или загрузите файл";
        }
        return result;

    }

}
