package com.title.Controller;

import com.title.Service.titleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class titleController {

    @Autowired
    titleService titleService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView title(@RequestParam(required = false) String userId){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("title");

        modelAndView.addObject("user",titleService.selectUser(userId));
        modelAndView.addObject("movies",titleService.selectMovies(userId));
        modelAndView.addObject("movies2",titleService.selectMovies2(userId));
        modelAndView.addObject("movies3",titleService.selectMovies3(userId));
        modelAndView.addObject("movies4",titleService.selectMovies4(userId));
        modelAndView.addObject("movies5",titleService.selectMovies5(userId));


        return modelAndView;
    }
}
