package com.example.wineshop;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TypeController {
    private final TypeRepository repository;

    TypeController(TypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/type/{id}")
    Type one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id));
    }

    @PutMapping("/api/type/{id}")
    Type replaceEmployee(@RequestBody Type newType, @PathVariable Long id) {

        return repository.findById(id)
                .map(type -> {
                    type.setName(newType.getName());

                    return repository.save(type);
                })
                .orElseGet(() -> {
                    newType.setId(id);
                    return repository.save(newType);
                });
    }

    @DeleteMapping("/api/type/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
