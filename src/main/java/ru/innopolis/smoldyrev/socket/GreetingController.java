package ru.innopolis.smoldyrev.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {


    @MessageMapping("/messageListener")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        Thread.sleep(100); // simulated delay
        System.out.println("//////"+message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public ModelAndView messagePage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("rooms/messagebox3");
        return modelAndView;
    }

}
