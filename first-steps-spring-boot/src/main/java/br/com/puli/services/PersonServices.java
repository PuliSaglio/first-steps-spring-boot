package br.com.puli.services;

import br.com.puli.data.vo.v1.PersonVO;
import br.com.puli.exceptions.ResourceNotFoundException;
import br.com.puli.mapper.DozerMapper;
import br.com.puli.mapper.custom.PersonMapper;
import br.com.puli.model.Person;
import br.com.puli.repositories.PersonRepository;
import data.vo.v2.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all PersonVOs...");
        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }


    public PersonVO findById(Long id) {
        logger.info("Finding one PersonVO...");

        var entity = repository.findById(id)
                .orElseThrow(
                ()-> new ResourceNotFoundException("Person not found for this id: " + id)
        );
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating new PersonVO...");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating new PersonVO...");
        var entity = mapper.convertVoToEntity(person);
        var vo = mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating PersonVO...");
        var entity = repository.findById(person.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Person not found for this id: " + person.getId())
                );

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting PersonVO...");

        var entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("PersonVO not found for this id: " + id)
                );

        repository.delete(entity);

    }
}
