package com.accela.person.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ADDRESS")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_PERSON")
    private Person person;

    @NotNull
    @Column(name = "CITY")
    private String city;

    @NotNull
    @Column(name = "STREET")
    private String street;

    @NotNull
    @Column(name = "STATE")
    private String state;

    @NotNull
    @Column(name = "POSTAL_CODE")
    private String postalCode;
}
