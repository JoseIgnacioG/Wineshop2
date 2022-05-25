package com.example.wineshop;

class RegionNotFoundException extends RuntimeException {

    RegionNotFoundException(Long id) {
        super("Could not find region " + id);
    }
}
