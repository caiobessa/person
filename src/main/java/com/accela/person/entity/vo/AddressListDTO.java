package com.accela.person.entity.vo;

import com.accela.person.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressListDTO {

    @NotNull
    @Size(min = 1, message = "address should not be empty")
    private List<Address> address;
}
