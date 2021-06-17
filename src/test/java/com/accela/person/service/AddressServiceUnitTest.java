package com.accela.person.service;


import com.accela.person.core.BusinessException;
import com.accela.person.entity.Address;
import com.accela.person.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AddressServiceUnitTest {

    @Test
    public void delete_notFound_throwException() {
        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.findById(eq(1L))).thenReturn(Optional.empty());
        AddressService service = new AddressService(addressRepository, null);
        Throwable exception = Assertions.assertThrows(BusinessException.class, () -> service.delete(10L));
        assertThat("Address not found").isEqualTo(exception.getMessage());
    }

    @Test
    public void delete_found_callRepositoryDelete() {
        AddressRepository addressRepository = mock(AddressRepository.class);
        Address address = new Address();
        address.setId(1L);
        when(addressRepository.findById(eq(1L))).thenReturn(Optional.of(address));
        AddressService service = new AddressService(addressRepository, null);
        service.delete(1L);
        verify(addressRepository).delete(eq(address));
    }

}
