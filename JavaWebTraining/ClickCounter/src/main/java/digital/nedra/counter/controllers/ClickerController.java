package digital.nedra.counter.controllers;

import digital.nedra.counter.entities.Click;
import digital.nedra.counter.repository.ClickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class ClickerController {
    @Autowired
    private ClickerRepository clickerRepository;

    @GetMapping
    public String clickController(Model model) {
        Optional<Click> clicks = clickerRepository.findById(1);
        Click click = null;
        if (clicks.isEmpty()) {
            click = new Click();
            click.setId(1);
            click.setCount(0);
            clickerRepository.save(click);
        }
        else click = clicks.get();

        model.addAttribute("click", click);
        return "index";
    }

    @PostMapping("/save")
    public String increment(Model model) {
        Click click = clickerRepository.findById(1).get();
        click.setCount(click.getCount()+1);
        clickerRepository.save(click);
        return "redirect:/";
    }
}
