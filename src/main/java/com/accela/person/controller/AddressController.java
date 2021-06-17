package com.accela.person.controller;

import com.accela.person.entity.Address;
import com.accela.person.entity.vo.AddressListDTO;
import com.accela.person.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    /**
     * This method is used to crete/update the address
     * if the id is null it will create, if is not it will update
     *
     * @param address
     * @return
     */
    @PostMapping
    public ResponseEntity<Address> save(@RequestBody @Valid Address address) {
        addressService.save(address);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @PostMapping(path = "/multiple")
    public ResponseEntity<AddressListDTO> saveMultiple(@RequestBody @Valid AddressListDTO addressListVO) {
        addressService.save(addressListVO);
        return ResponseEntity.status(HttpStatus.OK).body(addressListVO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressListDTO> delete(@PathVariable("id") Long id) {
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }
}
