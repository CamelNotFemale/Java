package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first") // теперь все методы Контроллера доступны по URL: /first/...
public class FirstController {

    @GetMapping("/hello")
    // первый способ получения параметров GET
    // (!!!) в случае передачи пустых параметров - присваивает им значения null
    public String helloPage(HttpServletRequest request) {
        String name = request.getParameter("name");
        System.out.println("Hello " + name);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    // второй способ получения параметров GET (получение исключительно нужных параметров)
    // (!!!) в случае передачи пустых параметров - возвращает код 404
    // для избежания такой проблемы можно использовать @RequestParam(value = "name", required = false)
    public String goodByePage(@RequestParam("name") String name) {
        System.out.println("Goodbye " + name);
        return "first/goodbye";
    }

}
