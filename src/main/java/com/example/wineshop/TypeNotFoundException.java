package com.example.wineshop;

class TypeNotFoundException extends RuntimeException {

        TypeNotFoundException(Long id) {
        super("Could not find type " + id);
    }
}
