package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT = 0;
    private List<Person> people;

    { // блок инициализации "Базы данных" при создании
        people = new ArrayList<>();

        people.add(new Person(PEOPLE_COUNT++, "Dmitriy", "Dementev", "check@mail.ru"));
        people.add(new Person(PEOPLE_COUNT++, "Evgeniy", "Royzman", "drugs@gmail.ru"));
        people.add(new Person(PEOPLE_COUNT++, "Viktor", "Pelevin", "utopi@mail.ru"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(PEOPLE_COUNT++);
        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setSurname(updatedPerson.getSurname());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
        people.remove(show(id));
    }
}
