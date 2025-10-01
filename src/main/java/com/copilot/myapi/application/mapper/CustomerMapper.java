package com.copilot.myapi.application.mapper;

import com.copilot.myapi.application.dto.CustomerDTO;
import com.copilot.myapi.application.dto.AddressDTO;
import com.copilot.myapi.infrastructure.entity.CustomerEntity;
import com.copilot.myapi.infrastructure.entity.AddressEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    
    public CustomerDTO toDTO(CustomerEntity entity) {
        if (entity == null) return null;
        
        return new CustomerDTO(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getCpf(),
            entity.getAddresses() != null
                ? entity.getAddresses().stream()
                    .map(this::toAddressDTO)
                    .collect(Collectors.toList())
                : new ArrayList<>()
        );
    }

    public CustomerEntity toEntity(CustomerDTO dto) {
        if (dto == null) return null;
        
        CustomerEntity entity = new CustomerEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setCpf(dto.cpf());
        
        if (dto.addresses() != null) {
            entity.setAddresses(dto.addresses().stream()
                .map(addressDTO -> {
                    AddressEntity addressEntity = toAddressEntity(addressDTO);
                    addressEntity.setCustomer(entity);
                    return addressEntity;
                })
                .collect(Collectors.toList()));
        }
        
        return entity;
    }

    public AddressDTO toAddressDTO(AddressEntity entity) {
        if (entity == null) return null;
        
        return new AddressDTO(
            entity.getId(),
            entity.getStreet(),
            entity.getNumber(),
            entity.getComplement(),
            entity.getNeighborhood(),
            entity.getCity(),
            entity.getState(),
            entity.getZipCode()
        );
    }

    public AddressEntity toAddressEntity(AddressDTO dto) {
        if (dto == null) return null;
        
        AddressEntity entity = new AddressEntity();
        entity.setId(dto.id());
        entity.setStreet(dto.street());
        entity.setNumber(dto.number());
        entity.setComplement(dto.complement());
        entity.setNeighborhood(dto.neighborhood());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setZipCode(dto.zipCode());
        return entity;
    }
}