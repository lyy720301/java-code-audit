package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @GetMapping("/calc")
    public ModelAndView calc() {
        ModelAndView modelAndView = new ModelAndView("calc");
        modelAndView.addObject("title", "Freemarker");
        return modelAndView;
    }

    @GetMapping("/calc2")
    public ModelAndView calc2() {
        ModelAndView modelAndView = new ModelAndView("calc2");
        return modelAndView;
    }

    @GetMapping("/assign")
    public ModelAndView assign() {
        ModelAndView modelAndView = new ModelAndView("assign");
        return modelAndView;
    }
}