package com.example.wineshop.controllers;

import java.util.List;

import com.example.wineshop.models.Region;
import com.example.wineshop.exceptions.RegionNotFoundException;
import com.example.wineshop.repositories.RegionRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionController {

    private final RegionRepository repository;

    RegionController(RegionRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/regions")
    List<Region> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/region")
    Region newRegion(@RequestBody Region newRegion) {
        return repository.save(newRegion);
    }

    // Single item

    @GetMapping("/region/{id}")
    Region one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    @PutMapping("/region/{id}")
    Region replaceRegion(@RequestBody Region newRegion, @PathVariable Long id) {

        return repository.findById(id)
                .map(Region -> {
                    Region.setName(newRegion.getName());
                    Region.setCountry(newRegion.getCountry());
                    return repository.save(Region);
                })
                .orElseGet(() -> {
                    newRegion.setId(id);
                    return repository.save(newRegion);
                });
    }

    @DeleteMapping("/region/{id}")
    void deleteRegion(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
