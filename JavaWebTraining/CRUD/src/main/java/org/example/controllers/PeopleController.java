package org.example.controllers;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    /** ДАО для работы с данными класса Person */
    private final PersonDAO personDAO;
    /** Текущая страница */
    private int page;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        this.page = page; // устанавливаем новую текущую страницу

        int peopleCount = personDAO.peopleCount();
        int pagesCount = (peopleCount+9)/10;

        // получим всех людей из DAO и передадим их на отображение в представление
        model.addAttribute("people", personDAO.index(page));
        // передаем информацию для создания страниц в таблице
        model.addAttribute("peopleCount", peopleCount);
        model.addAttribute("page", page);
        model.addAttribute("pagesCount", new int[pagesCount]);

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        // получим одного человека из DAO и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        // положили объект для использования в шаблонизаторе thymeleaf
        model.addAttribute("person", new Person());

        return "people/new";
    }
    /*
    можно заменить на метод с аннотацией ModelAttribute
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        // ModelAttribute создаст пустой объект Person сам
        return "people/new";
    }
     */

    @PostMapping()
    // BindingResult должен ВСЕГДА идти после @Valid
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        // если есть ошибки валидации
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);

        return "redirect:/people/?page=" + this.page;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        // получим одного человека из DAO и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        // если есть ошибки валидации
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);

        return "redirect:/people/?page=" + this.page;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);

        return "redirect:/people/?page=" + this.page;
    }
}
