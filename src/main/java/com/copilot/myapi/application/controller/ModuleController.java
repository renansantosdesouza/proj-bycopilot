package com.copilot.myapi.application.controller;

import com.copilot.myapi.application.dto.ModuleDTO;
import com.copilot.myapi.application.mapper.ModuleMapper;
import com.copilot.myapi.infrastructure.repository.ModuleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Modules", description = "Course module management APIs")
public class ModuleController {
    
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    public ModuleController(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    @PostMapping
    @Operation(summary = "Create module", description = "Create a new course module")
    public ResponseEntity<ModuleDTO> createModule(@Valid @RequestBody ModuleDTO moduleDTO) {
        var entity = moduleMapper.toEntity(moduleDTO);
        var savedEntity = moduleRepository.save(entity);
        return ResponseEntity.ok(moduleMapper.toDTO(savedEntity));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "List course modules", description = "Get all modules for a course")
    public ResponseEntity<List<ModuleDTO>> getCourseModules(@PathVariable Long courseId) {
        var modules = moduleRepository.findByCourseIdOrderByOrderNumberAsc(courseId);
        var dtos = modules.stream()
                .map(moduleMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get module", description = "Get module by ID")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Long id) {
        return moduleRepository.findById(id)
                .map(moduleMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update module", description = "Update an existing module")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable Long id, 
            @Valid @RequestBody ModuleDTO moduleDTO) {
        if (!moduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var entity = moduleMapper.toEntity(moduleDTO);
        entity.setId(id);
        var updatedEntity = moduleRepository.save(entity);
        return ResponseEntity.ok(moduleMapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete module", description = "Delete a module")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        if (!moduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        moduleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}