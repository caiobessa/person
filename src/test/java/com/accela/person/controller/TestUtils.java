package com.accela.person.controller;

import com.accela.person.entity.Address;
import com.accela.person.entity.Person;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.List;


public class TestUtils {

    private TestRestTemplate restTemplate;

    public TestUtils(TestRestTemplate testRestTemplate) {
        this.restTemplate = testRestTemplate;
    }

    public List<Address> createMultiple(int size) {
        var personId = createUser("Jane", "Smith");
        var person = new Person();
        person.setId(personId);

        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Address address = new Address();
            address.setCity("Dublin" + 1);
            address.setState("Dublin State" + 1);
            address.setStreet("8 Street" + 1);
            address.setPostalCode("00005" + 1);
            address.setPerson(person);
            addresses.add(address);
        }
        return addresses;
    }

    public Long createUser(String name, String lastName) {
        var person = new Person();
        person.setFirstName(name);
        person.setLastName(lastName);
        var id = restTemplate.postForEntity("/person", person, Person.class).getBody().getId();
        return id;
    }
}
