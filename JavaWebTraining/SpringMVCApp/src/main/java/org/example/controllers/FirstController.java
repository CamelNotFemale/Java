package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    // (!!!) для @RequestParam("name")в случае передачи пустых параметров - возвращает код 404
    // для избежания такой проблемы можно использовать @RequestParam(value = "name", required = false)
    public String goodByePage(@RequestParam(value = "name", required = false) String name,
                              Model model) {
        //System.out.println("Goodbye " + name);
        // передаём в Модель новый параметр, который может исопльзовать Отображение
        model.addAttribute("message", "Goodbye " + name);
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculate(@RequestParam("action") String action,
                            @RequestParam("a") int a,
                            @RequestParam("b") int b,
                              Model model) {
        String res = null;
        // выбираем арифметическое действие
        switch (action) {
            case "multiplication":
                res = a + " * " + b + " = " + a*b;
                break;
            case "addition":
                res = a + " + " + b + " = " + (a+b);
                break;
            case "subtraction":
                res = a + " - " + b + " = " + (a-b);
                break;
            case "division":
                res = a + " / " + b + " = " + a/b;
                break;
        }
        // передаём в Модель новый параметр, который может исопльзовать Отображение
        model.addAttribute("action", action);
        model.addAttribute("result", res);

        return "first/calculator";
    }
}
