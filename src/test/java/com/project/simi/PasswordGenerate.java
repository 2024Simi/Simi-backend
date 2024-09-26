package com.project.simi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerate extends SuperIntegrationTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void generatePassword() {
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("password: " + password);
        System.out.println("encodedPassword: " + encodedPassword);
    }

}
