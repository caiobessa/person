package com.accela.person.entity.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private Long id;

    private String city;

    private String street;

    private String state;

    private String postalCode;
}
