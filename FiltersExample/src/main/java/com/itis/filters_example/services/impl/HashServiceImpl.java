package com.itis.filters_example.services.impl;

import com.itis.filters_example.services.HashService;

public class HashServiceImpl implements HashService {
    @Override
    public String hash(String password) {
        return password;
    }

    @Override
    public boolean matches(String password, String hash) {
        return password.equals(hash);
    }
}
