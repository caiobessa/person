package com.accela.person.service;


import com.accela.person.core.BusinessException;
import com.accela.person.entity.Person;
import com.accela.person.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PersonServiceUnitTest {

    @Test
    public void getPersonById_notFound_throwException() {
        PersonRepository personService = mock(PersonRepository.class);
        when(personService.findById(eq(10L))).thenReturn(Optional.empty());
        PersonService service = new PersonService(personService);
        Throwable exception = Assertions.assertThrows(BusinessException.class, () -> service.getPersonById(10L));
        assertThat("Person not found").isEqualTo(exception.getMessage());
    }

    @Test
    public void getPersonById_foundPerson_returnPerson() {
        PersonRepository personService = mock(PersonRepository.class);
        Person person = new Person();
        person.setFirstName("Tales");
        person.setLastName("Smith");
        person.setId(5L);
        when(personService.findById(eq(5L))).thenReturn(Optional.of(person));
        PersonService service = new PersonService(personService);
        Person personFound = service.getPersonById(5L);

        assertThat(personFound.getId()).isEqualTo(5L);
        assertThat(personFound.getLastName()).isEqualTo("Smith");
        assertThat(personFound.getFirstName()).isEqualTo("Tales");
    }
}
