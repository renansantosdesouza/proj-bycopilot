package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.CustomerDTO;
import com.copilot.myapi.application.dto.AddressDTO;
import com.copilot.myapi.infrastructure.entity.CustomerEntity;
import com.copilot.myapi.infrastructure.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void shouldMapCustomerEntityToDTO() {
        // given
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1L);
        entity.setName("John Doe");
        entity.setEmail("john@example.com");
        entity.setCpf("12345678901");

        AddressEntity address = new AddressEntity();
        address.setId(1L);
        address.setStreet("Main Street");
        address.setNumber("123");
        address.setComplement("Apt 4B");
        address.setNeighborhood("Downtown");
        address.setCity("New York");
        address.setState("NY");
        address.setZipCode("10001");
        address.setCustomer(entity);

        entity.setAddresses(List.of(address));

        // when
        CustomerDTO dto = mapper.toDTO(entity);

        // then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getEmail(), dto.email());
        assertEquals(entity.getCpf(), dto.cpf());
        assertNotNull(dto.addresses());
        assertEquals(1, dto.addresses().size());

        AddressDTO addressDTO = dto.addresses().get(0);
        assertEquals(address.getId(), addressDTO.id());
        assertEquals(address.getStreet(), addressDTO.street());
        assertEquals(address.getNumber(), addressDTO.number());
        assertEquals(address.getComplement(), addressDTO.complement());
        assertEquals(address.getNeighborhood(), addressDTO.neighborhood());
        assertEquals(address.getCity(), addressDTO.city());
        assertEquals(address.getState(), addressDTO.state());
        assertEquals(address.getZipCode(), addressDTO.zipCode());
    }

    @Test
    void shouldMapCustomerDTOToEntity() {
        // given
        CustomerDTO dto = new CustomerDTO(1L, "John Doe", "john@example.com", "12345678901",
            List.of(new AddressDTO(1L, "Main Street", "123", "Apt 4B", "Downtown", "New York", "NY", "10001")));

        // when
        CustomerEntity entity = mapper.toEntity(dto);

        // then
        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.cpf(), entity.getCpf());
        assertNotNull(entity.getAddresses());
        assertEquals(1, entity.getAddresses().size());

        AddressEntity address = entity.getAddresses().get(0);
        assertEquals(dto.addresses().get(0).id(), address.getId());
        assertEquals(dto.addresses().get(0).street(), address.getStreet());
        assertEquals(dto.addresses().get(0).number(), address.getNumber());
        assertEquals(dto.addresses().get(0).complement(), address.getComplement());
        assertEquals(dto.addresses().get(0).neighborhood(), address.getNeighborhood());
        assertEquals(dto.addresses().get(0).city(), address.getCity());
        assertEquals(dto.addresses().get(0).state(), address.getState());
        assertEquals(dto.addresses().get(0).zipCode(), address.getZipCode());
        assertEquals(entity, address.getCustomer());
    }

    @Test
    void shouldHandleNullValues() {
        assertNull(mapper.toDTO(null));
        assertNull(mapper.toEntity(null));
    }
}