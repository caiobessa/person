package com.accela.person.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PERSON")
@Getter
@Setter
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PERSON")
    private Long id;

    @Column(name = "FIRST_NAME")
    @NotNull(message = "First name is mandatory")
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull(message = "Last name is mandatory")
    private String lastName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.REMOVE)
    List<Address> address;
}
