package com.example.wineshop.controllers;

import com.example.wineshop.models.Wine;
import com.example.wineshop.repositories.WineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RecommendController {

    private final WineRepository repository;

    public RecommendController(WineRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/recommend/best{top}")
    List<Wine> bests(@RequestParam(name="top") int top) {
        return repository.findAll()
                .stream().collect(Collectors.toList());
    }

    /*@GetMapping("/api/recommend/best")
    List<Wine> bests(@RequestParam(name="top") int top) {

    }

    @GetMapping("/api/recommend/bang")
    List<Wine> bests(@RequestParam(name="top") int top) {

    }

    @GetMapping("/api/recommends/vintage")
    List<Wine> bests(@RequestParam(name="top") int top) {

    }*/

}
