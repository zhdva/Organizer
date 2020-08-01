package org.zhadaev.organizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.zhadaev.organizer.service.ComparatorService;
import org.zhadaev.organizer.service.ExpanderService;
import org.zhadaev.organizer.service.FileService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OrganizerController {

    private ComparatorService comparatorService;
    private ExpanderService expanderService;
    private FileService fileService;
    private final String task1 = "Comparator";
    private final String task2 = "Expander";
    private Map<String, String> map;

    @Autowired
    public void setServices(final ComparatorService comparatorService, final ExpanderService expanderService, final FileService fileService) {
        this.comparatorService = comparatorService;
        this.expanderService = expanderService;
        this.fileService = fileService;
    }

    public OrganizerController() {
        map = new HashMap<>();
        map.put("message", "Выберите задачу или загрузите файл");
    }

    @GetMapping(value = "/")
    public ModelAndView organizer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Organizer");
        modelAndView.addObject("map", map);
        return modelAndView;
    }

    @PostMapping(value = "/calculate")
    public ModelAndView calculate(@RequestBody MultiValueMap<String, String> form) {

        map = form.toSingleValueMap();
        String result;
        switch (map.get("task")) {
            case task1:
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
            case task2:
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
        map.put("message", result);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
