package com.ChattingApp.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	private final BCryptPasswordEncoder passwordEncoder;

    public EncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String bcryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}