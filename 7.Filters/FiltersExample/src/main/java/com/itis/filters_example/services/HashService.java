package com.itis.filters_example.services;

public interface HashService {
    String hash(String password);
    boolean matches(String password, String hash);
}
