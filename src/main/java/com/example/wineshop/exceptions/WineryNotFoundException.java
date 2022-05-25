package com.example.wineshop.exceptions;

public class WineryNotFoundException extends RuntimeException {

    public WineryNotFoundException(Long id) {
        super("Could not find Winery " + id);
    }
}
