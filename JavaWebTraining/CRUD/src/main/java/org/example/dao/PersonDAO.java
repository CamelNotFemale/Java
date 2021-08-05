package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT = 0;
    private List<Person> people;

    { // блок инициализации при создании
        people = new ArrayList<>();

        people.add(new Person(PEOPLE_COUNT++, "Dima"));
        people.add(new Person(PEOPLE_COUNT++, "Katya"));
        people.add(new Person(PEOPLE_COUNT++, "Kolya"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
