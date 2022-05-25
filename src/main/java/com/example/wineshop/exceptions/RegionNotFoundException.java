package com.example.wineshop.exceptions;

public class RegionNotFoundException extends RuntimeException {

    public RegionNotFoundException(Long id) {
        super("Could not find region " + id);
    }
}
