package com.copilot.myapi.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {

    private final ValidationService validationService = new ValidationService();

    @Test
    void shouldValidateCorrectCPF() {
        assertTrue(validationService.isValidCPF("52998224725"));
        assertTrue(validationService.isValidCPF("529.982.247-25"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "11111111111",
        "00000000000",
        "12345678901",
        "999.999.999-99"
    })
    void shouldInvalidateIncorrectCPF(String cpf) {
        assertFalse(validationService.isValidCPF(cpf));
    }

    @Test
    void shouldInvalidateNullCPF() {
        assertFalse(validationService.isValidCPF(null));
    }

    @Test
    void shouldInvalidateEmptyCPF() {
        assertFalse(validationService.isValidCPF(""));
    }

    @Test
    void shouldInvalidateWrongLengthCPF() {
        assertFalse(validationService.isValidCPF("123456789"));
        assertFalse(validationService.isValidCPF("123456789012"));
    }
}