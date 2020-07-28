package org.zhadaev.organizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrganizerController {

    @GetMapping(value = "/")
    public ModelAndView organizerGet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Organizer");
        return modelAndView;
    }

    @PostMapping(value = "/")
    public ModelAndView organizerPost() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Organizer");
        return modelAndView;
    }

}
