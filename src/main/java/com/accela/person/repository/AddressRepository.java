package com.accela.person.repository;


import com.accela.person.entity.Address;
import com.accela.person.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> getByPerson(Person person);
}
