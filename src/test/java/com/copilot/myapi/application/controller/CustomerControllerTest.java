package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.CustomerDTO;
import com.copilot.myapi.application.dto.AddressDTO;
import com.copilot.myapi.application.mapper.CustomerMapper;
import com.copilot.myapi.infrastructure.entity.CustomerEntity;
import com.copilot.myapi.infrastructure.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;
    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO(1L, "John Doe", "john@example.com", "12345678901",
            List.of(new AddressDTO(1L, "Main Street", "123", "Apt 4B", "Downtown", "New York", "NY", "10001")));

        customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setName("John Doe");
        customerEntity.setEmail("john@example.com");
        customerEntity.setCpf("12345678901");
    }

    @Test
    void shouldCreateCustomer() {
        // given
        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(customerEntity);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerMapper.toDTO(any(CustomerEntity.class))).thenReturn(customerDTO);

        // when
        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(customerDTO, response.getBody());
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void shouldGetCustomerById() {
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.toDTO(customerEntity)).thenReturn(customerDTO);

        // when
        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1L);

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() {
        // given
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1L);

        // then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldUpdateCustomer() {
        // given
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(customerEntity);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerMapper.toDTO(any(CustomerEntity.class))).thenReturn(customerDTO);

        // when
        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(1L, customerDTO);

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(customerDTO, response.getBody());
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void shouldDeleteCustomer() {
        // given
        when(customerRepository.existsById(1L)).thenReturn(true);

        // when
        ResponseEntity<Void> response = customerController.deleteCustomer(1L);

        // then
        assertEquals(204, response.getStatusCode().value());
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentCustomer() {
        // given
        when(customerRepository.existsById(1L)).thenReturn(false);

        // when
        ResponseEntity<Void> response = customerController.deleteCustomer(1L);

        // then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldGetAllCustomers() {
        // given
        List<CustomerEntity> entities = List.of(customerEntity);
        when(customerRepository.findAll()).thenReturn(entities);
        when(customerMapper.toDTO(any(CustomerEntity.class))).thenReturn(customerDTO);

        // when
        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(customerDTO, response.getBody().get(0));
    }
}