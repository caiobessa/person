package com.accela.person.controller;

import com.accela.person.entity.Address;
import com.accela.person.entity.Person;
import com.accela.person.entity.vo.AddressListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@EnableJpaRepositories
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private TestUtils testUtils;

    @BeforeEach
    public void setup() {
        testUtils = new TestUtils(restTemplate);
    }


    @Test
    public void save_OneAddress_ReturnSuccess() {
        Long id = testUtils.createUser("Maria", "Smith");
        Person person = new Person();
        person.setId(id);
        Address address = new Address();
        address.setCity("Dublin");
        address.setState("Dublin State");
        address.setStreet("8 Street");
        address.setPostalCode("00005");
        address.setPerson(person);
        Address addressResult = restTemplate.postForEntity("/address", address, Address.class).getBody();

        assertThat(addressResult).isNotNull();
        assertThat(addressResult.getId()).isNotNull();
        assertThat(addressResult.getCity()).isEqualTo("Dublin");
        assertThat(addressResult.getPostalCode()).isEqualTo("00005");
        assertThat(addressResult.getState()).isEqualTo("Dublin State");
        assertThat(addressResult.getStreet()).isEqualTo("8 Street");
        assertThat(addressResult.getPerson().getId()).isEqualTo(id);

    }

    @Test
    public void delete_OneAddress_ReturnSuccess() {
        Long id = testUtils.createUser("Maria", "Smith");
        Person person = new Person();
        person.setId(id);
        Address address = new Address();
        address.setCity("Dublin");
        address.setState("Dublin State");
        address.setStreet("8 Street");
        address.setPostalCode("00005");
        address.setPerson(person);

        Address addressResult = restTemplate.postForEntity("/address", address, Address.class).getBody();

        var result = restTemplate.exchange("/address/" + addressResult.getId(), HttpMethod.DELETE, null, Map.class).getStatusCode();

        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteById_inValidId_returnError() {
        var error = restTemplate.exchange("/address/2323232", HttpMethod.DELETE, null, Map.class).getBody();
        assertThat(error.get("message")).isEqualTo("Address not found");
    }

    @Test
    public void save_invalidPerson_ReturnErrorMessage() {
        Address address = new Address();
        address.setCity("Dublin");
        address.setState("Dublin State");
        address.setStreet("8 Street");
        address.setPostalCode("00005");
        Person person = new Person();
        person.setId(34234234L);
        address.setPerson(person);
        Map<String, String> map = restTemplate.postForEntity("/address", address, HashMap.class).getBody();

        assertThat(map).isNotNull();
        assertThat(map.get("message")).isEqualTo("Person not found");
    }

    @Test
    public void save_emptyObject_ReturnErrorMessage() {
        Address address = new Address();
        Map<String, String> map = restTemplate.postForEntity("/address", address, HashMap.class).getBody();

        assertThat(map).isNotNull();
        assertThat(map.get("city")).isEqualTo("must not be null");
        assertThat(map.get("postalCode")).isEqualTo("must not be null");
        assertThat(map.get("person")).isEqualTo("must not be null");
        assertThat(map.get("postalCode")).isEqualTo("must not be null");
        assertThat(map.get("state")).isEqualTo("must not be null");
    }

    @Test
    public void saveMultiple_listOfAddress_Return5Elements() {
        List<Address> listOfAddress = testUtils.createMultiple(5);
        AddressListDTO addressListVO = new AddressListDTO();
        addressListVO.setAddress(listOfAddress);
        var addressVOResult = restTemplate.postForEntity("/address/multiple", addressListVO, AddressListDTO.class).getBody();

        assertThat(addressVOResult).isNotNull();
        assertThat(addressVOResult.getAddress().size()).isEqualTo(5);
        addressVOResult.getAddress().stream().forEach(a -> {
            assertThat(a.getId()).isNotNull();
        });
    }

    @Test
    public void saveMultiple_emptyList_returnError() {
        AddressListDTO addressListVO = new AddressListDTO();
        addressListVO.setAddress(new ArrayList<>());
        var map = restTemplate.postForEntity("/address/multiple", addressListVO, HashMap.class).getBody();
        assertThat(map).isNotNull();
        assertThat(map.get("address")).isEqualTo("address should not be empty");
    }


}
