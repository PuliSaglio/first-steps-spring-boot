package br.com.puli.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.puli.data.vo.v1.PersonVO;
import br.com.puli.model.Person;

public class DozerMapper {

    private static ModelMapper mapper = new ModelMapper();

    static {
        mapper.createTypeMap(
                        Person.class,
                        PersonVO.class)
                .addMapping(Person::getId, PersonVO::setId);
        mapper.createTypeMap(
                        PersonVO.class,
                        Person.class)
                .addMapping(PersonVO::getId, Person::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }

}