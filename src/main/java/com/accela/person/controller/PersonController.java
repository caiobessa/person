package com.accela.person.controller;

import com.accela.person.entity.Person;
import com.accela.person.entity.vo.AddressListDTO;
import com.accela.person.entity.vo.CountDTO;
import com.accela.person.entity.vo.PersonListDTO;
import com.accela.person.service.AddressService;
import com.accela.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    private final AddressService addressService;


    /**
     * This method is used to crete/update the person
     * if the id is null it will create, if is not it will update
     *
     * @param person
     * @return
     */
    @PostMapping
    public ResponseEntity<Person> save(@Valid @RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/count")
    public ResponseEntity<CountDTO> countAll() {
        CountDTO countVO = new CountDTO(personService.countAllUsers());
        return ResponseEntity.ok(countVO);
    }

    /**
     * note : this method in a real application should use pagination
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<PersonListDTO> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping(path = "/{id}/address")
    public ResponseEntity<AddressListDTO> getAllAddressByPerson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(addressService.getAllByPerson(id));
    }


}
