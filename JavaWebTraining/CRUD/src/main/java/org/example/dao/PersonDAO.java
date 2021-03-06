package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.example.models.Person;

import java.util.Comparator;
import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;
    /** Количество отображаемых страниц из БД */
    private int PAGE_COUNT = 10;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public int peopleCount() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(p) from Person p", Number.class).getSingleResult().intValue();
    }
    @Transactional(readOnly = true)
    public List<Person> index(int page) {
        Session session = sessionFactory.getCurrentSession();

        List<Person> resultList = session.createQuery("select p from Person p", Person.class)
                .setFirstResult(PAGE_COUNT * (page - 1)).setMaxResults(PAGE_COUNT).getResultList();

        resultList.sort(Comparator.comparing(Person::getId));

        return resultList;
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}
