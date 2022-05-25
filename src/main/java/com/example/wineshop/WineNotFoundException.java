package com.example.wineshop;

class WineNotFoundException extends RuntimeException {

    WineNotFoundException(Long id) {
        super("Could not find wine " + id);
    }
}
