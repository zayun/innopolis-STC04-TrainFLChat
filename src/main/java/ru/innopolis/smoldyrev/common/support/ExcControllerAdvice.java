package ru.innopolis.smoldyrev.common.support;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExcControllerAdvice {

    private static Logger logger = Logger.getLogger(ExcControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView notFoundError(Exception e) {
        logger.error(e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg", e.getMessage());
        return modelAndView;
    }
}
