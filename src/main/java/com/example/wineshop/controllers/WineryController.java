package com.example.wineshop.controllers;

import java.util.List;

import com.example.wineshop.models.Winery;
import com.example.wineshop.exceptions.WineryNotFoundException;
import com.example.wineshop.repositories.WineryRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WineryController {

    private final WineryRepository repository;

    WineryController(WineryRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/winerys")
    List<Winery> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/winery")
    Winery newWinery(@RequestBody Winery newWinery) {
        return repository.save(newWinery);
    }

    // Single item

    @GetMapping("/api/winery/{id}")
    Winery one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new WineryNotFoundException(id));
    }

    @PutMapping("/api/winery/{id}")
    Winery replaceWinery(@RequestBody Winery newWinery, @PathVariable Long id) {

        return repository.findById(id)
                .map(Winery -> {
                    Winery.setName(newWinery.getName());
                    return repository.save(Winery);
                })
                .orElseGet(() -> {
                    newWinery.setId(id);
                    return repository.save(newWinery);
                });
    }

    @DeleteMapping("/api/winery/{id}")
    void deleteWinery(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
