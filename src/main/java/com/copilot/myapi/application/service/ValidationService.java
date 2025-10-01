package com.copilot.myapi.application.service;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    
    public boolean isValidCPF(String cpf) {
        if (cpf == null) return false;
        
        // Remove special characters
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Must be 11 digits
        if (cpf.length() != 11) return false;
        
        // Check if all digits are the same
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        // Calculate first digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;
        
        // Calculate second digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;
        
        // Compare calculated digits with actual digits
        return cpf.charAt(9) - '0' == firstDigit && cpf.charAt(10) - '0' == secondDigit;
    }
}