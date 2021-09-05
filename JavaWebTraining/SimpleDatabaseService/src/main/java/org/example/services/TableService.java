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

    public void save(String key, TableEntry entry){
        tableRepository.set(key, entry);
    }

    public void save(String key, TableEntry entry, long ttl){
        tableRepository.set(key, entry, ttl);
    }

    public TableEntry get(String key) {
        return tableRepository.get(key);
    }

    //возвращает лист всех сущностей из базы
    public List<String[]> getAll(){
        return tableRepository.findAll(); //реализовали метод внедренного бина
    }
}
