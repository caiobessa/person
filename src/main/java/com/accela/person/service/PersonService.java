package com.accela.person.service;

import com.accela.person.core.BusinessException;
import com.accela.person.entity.Person;
import com.accela.person.entity.vo.PersonDTO;
import com.accela.person.entity.vo.PersonListDTO;
import com.accela.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final PersonRepository personRepository;

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public Person getPersonById(Long id) {
        Optional<Person> personById = personRepository.findById(id);
        return personById.orElseThrow(() -> new BusinessException("Person not found"));
    }

    @Transactional
    public void deleteById(Long id) {
        Person personById = getPersonById(id);
        personRepository.delete(personById);
    }

    public Long countAllUsers() {
        return personRepository.count();
    }

    public PersonListDTO getAll() {
        var listDTO = personRepository.findAllPerson()
                .stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .collect(Collectors.toList());
        return new PersonListDTO(listDTO);
    }
}
