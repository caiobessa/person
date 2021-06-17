package com.accela.person.repository;

import com.accela.person.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query(value = "select DISTINCT c from Person c left join fetch c.address ")
    public List<Person> findAllPerson();

}
