package net.yarik.todolist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserspaceController {

    private Logger log = LoggerFactory.getLogger(UserspaceController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView chat() {
        log.info("requested GET on /");
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

}
