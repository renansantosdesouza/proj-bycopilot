package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.ModuleDTO;
import com.copilot.myapi.application.mapper.ModuleMapper;
import com.copilot.myapi.infrastructure.entity.ModuleEntity;
import com.copilot.myapi.infrastructure.repository.ModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ModuleControllerTest {

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private ModuleMapper moduleMapper;

    @InjectMocks
    private ModuleController moduleController;

    private ModuleDTO moduleDTO;
    private ModuleEntity moduleEntity;

    @BeforeEach
    void setUp() {
        moduleDTO = new ModuleDTO(1L, "Module 1", "Description", 1, 1L, List.of());
        
        moduleEntity = new ModuleEntity();
        moduleEntity.setId(1L);
        moduleEntity.setTitle("Module 1");
        moduleEntity.setDescription("Description");
        moduleEntity.setOrderNumber(1);
    }

    @Test
    void shouldCreateModule() {
        when(moduleMapper.toEntity(any(ModuleDTO.class))).thenReturn(moduleEntity);
        when(moduleRepository.save(any(ModuleEntity.class))).thenReturn(moduleEntity);
        when(moduleMapper.toDTO(any(ModuleEntity.class))).thenReturn(moduleDTO);

        ResponseEntity<ModuleDTO> response = moduleController.createModule(moduleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(moduleRepository).save(any(ModuleEntity.class));
    }

    @Test
    void shouldGetModuleById() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(moduleEntity));
        when(moduleMapper.toDTO(moduleEntity)).thenReturn(moduleDTO);

        ResponseEntity<ModuleDTO> response = moduleController.getModuleById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(moduleDTO, response.getBody());
    }

    @Test
    void shouldReturnNotFoundForInvalidId() {
        when(moduleRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<ModuleDTO> response = moduleController.getModuleById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldGetCourseModules() {
        when(moduleRepository.findByCourseIdOrderByOrderNumberAsc(1L))
            .thenReturn(List.of(moduleEntity));
        when(moduleMapper.toDTO(any(ModuleEntity.class))).thenReturn(moduleDTO);

        ResponseEntity<List<ModuleDTO>> response = moduleController.getCourseModules(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldUpdateModule() {
        when(moduleRepository.existsById(1L)).thenReturn(true);
        when(moduleMapper.toEntity(any(ModuleDTO.class))).thenReturn(moduleEntity);
        when(moduleRepository.save(any(ModuleEntity.class))).thenReturn(moduleEntity);
        when(moduleMapper.toDTO(any(ModuleEntity.class))).thenReturn(moduleDTO);

        ResponseEntity<ModuleDTO> response = moduleController.updateModule(1L, moduleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(moduleRepository).save(any(ModuleEntity.class));
    }

    @Test
    void shouldDeleteModule() {
        when(moduleRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = moduleController.deleteModule(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(moduleRepository).deleteById(1L);
    }
}