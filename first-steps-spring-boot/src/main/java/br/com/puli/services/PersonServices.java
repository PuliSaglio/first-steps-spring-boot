package br.com.puli.services;

import br.com.puli.controllers.PersonController;
import br.com.puli.data.vo.v1.PersonVO;
import br.com.puli.exceptions.RequiredObjectIsNullException;
import br.com.puli.exceptions.ResourceNotFoundException;
import br.com.puli.mapper.DozerMapper;
import br.com.puli.model.Person;
import br.com.puli.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll() {
        logger.info("Finding all PersonVOs...");
        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }


    public PersonVO findById(Long id) {
        logger.info("Finding one PersonVO...");

        var entity = repository.findById(id)
                .orElseThrow(
                ()-> new ResourceNotFoundException("Person not found for this id: " + id)
        );
        var dto = DozerMapper.parseObject(entity, PersonVO.class);
        
        addHateoasLinks(dto);
        return dto;
    }

    public PersonVO create(PersonVO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        addHateoasLinks(dto);
        return dto;
    }

    public PersonVO update(PersonVO person) {
        if(person == null) throw new RequiredObjectIsNullException();
        logger.info("Updating PersonVO...");
        var entity = repository.findById(person.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Person not found for this id: " + person.getId())
                );

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = DozerMapper.parseObject(entity, PersonVO.class);

        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting PersonVO...");

        var entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("PersonVO not found for this id: " + id)
                );

        repository.delete(entity);

    }

    private void addHateoasLinks(PersonVO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).getAll()).withRel("getAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

    }
}
