package com.accela.person.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDTO {

    private Long id;

    private String firstName;

    private String lastName;

    List<AddressDTO> address;
}
