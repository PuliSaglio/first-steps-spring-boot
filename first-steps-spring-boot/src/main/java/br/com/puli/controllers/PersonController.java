package br.com.puli.controllers;

import br.com.puli.data.vo.v1.PersonVO;
import br.com.puli.model.Person;
import br.com.puli.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE ,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public List<PersonVO> getAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {
            MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    public PersonVO findById(
            @PathVariable(value = "id") Long id){
        return service.findById(id);

    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE ,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
    produces = {
            MediaType.APPLICATION_JSON_VALUE ,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public PersonVO create(@RequestBody PersonVO person){
        return service.create(person);
    }

    @PutMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE ,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
    produces = {
            MediaType.APPLICATION_JSON_VALUE ,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public PersonVO update(@RequestBody PersonVO person){
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
