package digital.nedra.counter.controllers;

import digital.nedra.counter.entities.Click;
import digital.nedra.counter.repository.ClickerRepository;
import digital.nedra.counter.services.ClickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/")
public class ClickerController {
    @Autowired
    private ClickerService clickerService;

    @GetMapping
    public String clickController(Model model) {
        Click click = clickerService.getCounter();
        model.addAttribute("click", click);
        return "index";
    }

    @PostMapping
    public String increment(Model model) {
        Click click = clickerService.increment();
        model.addAttribute("click", click);
        return "index";
    }
}
