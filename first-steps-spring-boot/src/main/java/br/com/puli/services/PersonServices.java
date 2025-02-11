package br.com.puli.services;

import br.com.puli.exceptions.ResourceNotFoundException;
import br.com.puli.model.Person;
import br.com.puli.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {
        logger.info("Finding all Persons...");
        return repository.findAll();
    }


    public Person findById(Long id) {
        logger.info("Finding one Person...");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found for this id: " + id));
    }

    public Person create(Person person) {
        logger.info("Creating new Person...");
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating Person...");
        Person entity = repository.findById(person.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Person not found for this id: " + person.getId())
                );

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting Person...");

        Person entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Person not found for this id: " + id)
                );

        repository.delete(entity);

    }
}
