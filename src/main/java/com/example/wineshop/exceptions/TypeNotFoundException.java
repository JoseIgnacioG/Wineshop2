package com.example.wineshop.exceptions;

public class TypeNotFoundException extends RuntimeException {

        public TypeNotFoundException(Long id) {
        super("Could not find type " + id);
    }
}
