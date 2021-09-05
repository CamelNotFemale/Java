package org.example.controllers;

import org.example.models.TableEntry;
import org.example.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tables")
public class DatabaseController {
    /** Сервис для работы с данными класса TableEntry */
    private final TableService tableService;

    @Autowired
    public DatabaseController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping()
    public String index(Model model) {
        TableEntry entry1 = new TableEntry("10".getBytes());

        TableEntry entry2 = new TableEntry("20".getBytes());

        TableEntry entry3 = new TableEntry("30".getBytes());

        //с помощью переменной сервиса вызываем методы сохранения в базу, по разу для одного объекта
        tableService.save("key_1", entry1);
        tableService.save("key_2", entry2);
        tableService.save("key_3", entry3);

        // передаем информацию для создания страниц в таблице
        model.addAttribute("entry", tableService.get("key_2"));

        return "index";
    }
/*
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
    */
}
