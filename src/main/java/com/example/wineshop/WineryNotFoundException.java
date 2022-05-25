package com.example.wineshop;

class WineryNotFoundException extends RuntimeException {

    WineryNotFoundException(Long id) {
        super("Could not find Winery " + id);
    }
}
