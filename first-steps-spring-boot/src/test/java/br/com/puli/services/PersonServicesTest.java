package br.com.puli.services;

import br.com.puli.data.vo.v1.PersonVO;
import br.com.puli.exceptions.RequiredObjectIsNullException;
import br.com.puli.model.Person;
import br.com.puli.repositories.PersonRepository;
import br.com.puli.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Address Test1" , result.getAddress());
        assertEquals("First Name Test1" , result.getFirstName());
        assertEquals("Last Name Test1" , result.getLastName());
        assertEquals("Female" , result.getGender());
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonVO personVO = input.mockVO(1);

        when(repository.save(person)).thenReturn((persisted));
        var result = service.create(personVO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Address Test1" , result.getAddress());
        assertEquals("First Name Test1" , result.getFirstName());
        assertEquals("Last Name Test1" , result.getLastName());
        assertEquals("Female" , result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
            service.create(null);
                });

        assertEquals("Required object is null", exception.getMessage());
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);
        PersonVO personVO = input.mockVO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));


        var result = service.update(personVO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Address Test1" , result.getAddress());
        assertEquals("First Name Test1" , result.getFirstName());
        assertEquals("Last Name Test1" , result.getLastName());
        assertEquals("Female" , result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        assertEquals("Required object is null", exception.getMessage());
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<PersonVO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14 , people.size());

        var personOne = people.get(1);

        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());
        assertNotNull(personOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(personOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(personOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(personOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(personOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Address Test1" , personOne.getAddress());
        assertEquals("First Name Test1" , personOne.getFirstName());
        assertEquals("Last Name Test1" , personOne.getLastName());
        assertEquals("Female" , personOne.getGender());

        var personSeven = people.get(7);

        assertNotNull(personSeven);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getLinks());
        assertNotNull(personSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(personSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(personSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(personSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(personSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Address Test7" , personSeven.getAddress());
        assertEquals("First Name Test7" , personSeven.getFirstName());
        assertEquals("Last Name Test7" , personSeven.getLastName());
        assertEquals("Female" , personSeven.getGender());

        var personTen = people.get(10);

        assertNotNull(personTen);
        assertNotNull(personTen.getId());
        assertNotNull(personTen.getLinks());
        assertNotNull(personTen.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(personTen.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        );
        assertNotNull(personTen.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(personTen.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(personTen.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Address Test10" , personTen.getAddress());
        assertEquals("First Name Test10" , personTen.getFirstName());
        assertEquals("Last Name Test10" , personTen.getLastName());
        assertEquals("Male" , personTen.getGender());


    }
}