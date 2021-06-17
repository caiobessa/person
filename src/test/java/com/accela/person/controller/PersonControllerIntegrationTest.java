package com.accela.person.controller;


import com.accela.person.entity.Address;
import com.accela.person.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

import com.accela.person.entity.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableJpaRepositories
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private TestUtils testUtils;

    @BeforeEach
    public void setup() {
        testUtils = new TestUtils(restTemplate);
    }

    @Test
    public void save_whenDoAPost_returnWithId() {
        var person = new Person();
        person.setFirstName("James");
        person.setLastName("Smith");
        var response = restTemplate.postForEntity("/person", person, Person.class);
        var personResponse = response.getBody();

        assertThat(personResponse.getId()).isNotNull();
        assertThat(personResponse.getFirstName()).isEqualTo("James");
        assertThat(personResponse.getLastName()).isEqualTo("Smith");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void save_withEmptyPost_returnError() {
        var person = new Person();
        var response = restTemplate.postForEntity("/person", person, HashMap.class);
        Map<String, String> responseEntity = response.getBody();

        assertThat(responseEntity.get("firstName")).isEqualTo("First name is mandatory");
        assertThat(responseEntity.get("lastName")).isEqualTo("Last name is mandatory");
    }

    @Test
    public void edit_changeNameAndLastName_returnUser() {
        Long id = testUtils.createUser("Mike", "Smith");
        var person = new Person();
        person.setId(id);
        person.setFirstName("Mike2");
        person.setLastName("Smith2");
        restTemplate.postForEntity("/person", person, HashMap.class);

        var personChanged = restTemplate.getForEntity("/person/" + id, Person.class).getBody();

        assertThat(personChanged).isNotNull();
        assertThat(personChanged.getId()).isEqualTo(id);
        assertThat(personChanged.getFirstName()).isEqualTo("Mike2");
        assertThat(personChanged.getLastName()).isEqualTo("Smith2");
    }

    @Test
    public void getPersonById_passIdViaGet_returnUser() {
        Long id = testUtils.createUser("Mike", "Smith");
        var personFound = restTemplate.getForEntity("/person/" + id, Person.class).getBody();

        assertThat(personFound).isNotNull();
        assertThat(personFound.getId()).isEqualTo(id);
        assertThat(personFound.getFirstName()).isEqualTo("Mike");
        assertThat(personFound.getLastName()).isEqualTo("Smith");
    }

    @Test
    public void getPersonById_passInvalidId_returnError() {
        var error = restTemplate.getForEntity("/person/23324324324", HashMap.class).getBody();
        assertThat(error.get("message")).isEqualTo("Person not found");
    }

    @Test
    public void deleteById_inValidId_returnError() {
        var error = restTemplate.exchange("/person/23324324324", HttpMethod.DELETE, null, Map.class).getBody();
        assertThat(error.get("message")).isEqualTo("Person not found");
    }

    @Test
    public void deleteById_validId_returnSuccess() {
        Long id = testUtils.createUser("John", "Smith");
        var response = restTemplate.exchange("/person/" + id, HttpMethod.DELETE, null, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var error = restTemplate.getForEntity("/person/" + id, HashMap.class).getBody();
        assertThat(error.get("message")).isEqualTo("Person not found");

    }

    @Test
    public void getAllAddress_byPerson_returnListOfAddress() {
        List<Address> multiple = testUtils.createMultiple(6);
        Long personId = multiple.get(0).getPerson().getId();
        AddressListDTO addressListVO = new AddressListDTO(multiple);
        restTemplate.postForEntity("/address/multiple", addressListVO, HashMap.class);

        var addressVOResult = restTemplate.getForEntity("/person/" + personId, AddressListDTO.class).getBody();
        assertThat(addressListVO.getAddress().size()).isEqualTo(6);

    }

    @Test
    public void count_create3people_returnMoreOrEquals3() {
        testUtils.createUser("John", "Smith");
        testUtils.createUser("John2", "Smith");
        testUtils.createUser("John3", "Smith");

        var count = restTemplate.getForEntity("/person/count", CountDTO.class).getBody();
        assertThat(count.getTotal() >= 3).isTrue();
    }

    @Test
    public void getAll_create4people_returMoreOrEquals4() {
        testUtils.createUser("John", "Smith");
        testUtils.createUser("John2", "Smith");
        testUtils.createUser("John3", "Smith");
        testUtils.createUser("John4", "Smith");

        var all = restTemplate.getForEntity("/person", PersonListDTO.class).getBody();
        assertThat(all.getList().size() >= 4).isTrue();
    }


    @Test
    public void getAll_personWIthAddress_returnAddress() {
        List<Address> multiple = testUtils.createMultiple(1);
        Long personId = multiple.get(0).getPerson().getId();
        AddressListDTO addressListVO = new AddressListDTO(multiple);
        restTemplate.postForEntity("/address", addressListVO.getAddress().get(0), HashMap.class);

        var all = restTemplate.getForEntity("/person", PersonListDTO.class).getBody();
        PersonDTO personVO = all.getList()
                .stream()
                .filter(p -> p.getId().equals(personId))
                .findFirst().get();

        AddressDTO addressVO = personVO.getAddress().get(0);
        assertThat(addressVO.getId()).isNotNull();
        assertThat(addressVO.getCity()).isNotNull();
        assertThat(addressVO.getState()).isNotNull();
        assertThat(addressVO.getCity()).isNotNull();
        assertThat(addressVO.getCity()).isNotNull();
    }

}
