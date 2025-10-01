package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.CustomerDTO;
import com.copilot.myapi.application.mapper.CustomerMapper;
import com.copilot.myapi.infrastructure.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Customer management APIs")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer with the provided information")
    @ApiResponse(responseCode = "200", description = "Customer created successfully")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        var entity = customerMapper.toEntity(customerDTO);
        var savedEntity = customerRepository.save(entity);
        return ResponseEntity.ok(customerMapper.toDTO(savedEntity));
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers")
    @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        var customers = customerRepository.findAll();
        var dtos = customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their ID")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Updates an existing customer's information")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var entity = customerMapper.toEntity(new CustomerDTO(id, customerDTO.name(), customerDTO.email(), 
            customerDTO.cpf(), customerDTO.addresses()));
        var updatedEntity = customerRepository.save(entity);
        return ResponseEntity.ok(customerMapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Deletes a customer by their ID")
    @ApiResponse(responseCode = "204", description = "Customer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}