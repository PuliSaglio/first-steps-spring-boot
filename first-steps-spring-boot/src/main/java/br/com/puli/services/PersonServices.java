package br.com.puli.services;

import br.com.puli.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        logger.info("Finding all persons...");
        for( int i = 0; i < 5; i++ ) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }


    public Person findById(String id) {
        logger.info("Finding one Person...");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setAddress("Natal-RN");
        person.setGender("Male");
        person.setFirstName("Puli");
        person.setLastName("Rossini");

        return person;
    }

    public Person create(Person person) {
        logger.info("Creating new Person...");
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating Person...");
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting Person...");
    }

    private Person mockPerson(int id) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("First Name " + id);
        person.setLastName("Last Name " + id);
        person.setAddress("Address " + id);
        person.setGender("Male");


        return person;
    }
}
