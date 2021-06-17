package com.accela.person.service;


import com.accela.person.core.BusinessException;
import com.accela.person.entity.Address;
import com.accela.person.entity.vo.AddressListDTO;
import com.accela.person.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final PersonService personService;

    @Transactional
    public void save(Address address) {
        var person = personService.getPersonById(address.getPerson().getId());
        address.setPerson(person);
        addressRepository.save(address);
    }

    @Transactional
    public void save(AddressListDTO addressListVO) {
        addressListVO.getAddress().stream().forEach(address -> {
            save(address);
        });
    }

    public AddressListDTO getAllByPerson(Long personId) {
        var person = personService.getPersonById(personId);
        var list = addressRepository.getByPerson(person);
        var addressListVO = new AddressListDTO();
        addressListVO.setAddress(list);
        return addressListVO;
    }

    @Transactional
    public void delete(Long id) {
        var optional = addressRepository.findById(id);
        var address = optional.orElseThrow(() -> new BusinessException("Address not found"));
        addressRepository.delete(address);
    }
}
