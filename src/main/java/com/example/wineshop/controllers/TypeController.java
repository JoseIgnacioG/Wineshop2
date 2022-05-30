package com.example.wineshop.controllers;

import com.example.wineshop.models.Region;
import com.example.wineshop.models.Type;
import com.example.wineshop.exceptions.TypeNotFoundException;
import com.example.wineshop.repositories.TypeRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TypeController {
    private final TypeRepository repository;

    TypeController(TypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/types")
    List<Type> all() {
        return repository.findAll();
    }

    @PostMapping("/types")
    Type newType(@Valid @RequestBody Type newType) {
        return repository.save(newType);
    }

    @GetMapping("/api/type/{id}")
    Type one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id));
    }

    @PutMapping("/api/type/{id}")
    Type replaceEmployee(@Valid @RequestBody Type newType, @PathVariable Long id) {

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
