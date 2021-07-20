package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello-world")
    public String sayHello() {
        // возвращаем представление из папки /WEB-INF/views,
        // путь и расширение указаны в конфиге applicationContextMVC.xml
        return "hello_world";
    }
}
