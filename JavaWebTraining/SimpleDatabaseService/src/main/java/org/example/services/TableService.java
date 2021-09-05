package org.example.services;

import org.example.models.TableEntry;
import org.example.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//помечаем что этот бин - сервис
public class TableService {
    private final TableRepository tableRepository;  //final переменная репозитория

    @Autowired
    public TableService(TableRepository tableRepository) {//внедрили зависимость через конструктор
        this.tableRepository = tableRepository;
    }

    //создали публичный метод (название любое может быть)
    //на вход принимает сущность и сохраняет ее в базу
    public void save(TableEntry entry){
        tableRepository.save(entry); //реализовали метод внедренного бина
    }
    //возвращает лист всех сущностей из базы
    public List<TableEntry> getAll(){
        return tableRepository.findAll(); //реализовали метод внедренного бина
    }
}
