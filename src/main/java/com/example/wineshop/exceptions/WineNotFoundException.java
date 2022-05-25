package com.example.wineshop.exceptions;

public class WineNotFoundException extends RuntimeException {

    public WineNotFoundException(Long id) {
        super("Could not find wine " + id);
    }
}
