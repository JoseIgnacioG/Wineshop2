package com.example.wineshop.controllers;

import com.example.wineshop.models.Wine;
import com.example.wineshop.repositories.WineRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RecommendController {

    private final WineRepository wineRepository;

    public RecommendController(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    @GetMapping("/api/recommend/best{top}")
    List<Wine> bests(@RequestParam(name="top" , defaultValue = "10") int top) {
        return wineRepository.findAllByOrderByRatingDesc(PageRequest.of(0, top));
    }

    @GetMapping("/api/recommend/expensive{top}")
    List<Wine> expensive(@RequestParam(name="top" , defaultValue = "10") int top) {
        return wineRepository.findAllByOrderByPriceDesc(PageRequest.of(0, top));
    }

    @GetMapping("/api/recommend/bang{top}")
    List<Wine> bang(@RequestParam(name="top" , defaultValue = "10") int top) {
        return wineRepository.findBestsRatingPrice(PageRequest.of(0, top));
    }

    static class VintageResponse {
        String vintage;
        List<Wine> wines;

        public VintageResponse(String vintage, List<Wine> wines) {
            this.vintage = vintage;
            this.wines = wines;
        }

        public String getVintage() {
            return vintage;
        }

        public void setVintage(String vintage) {
            this.vintage = vintage;
        }

        public List<Wine> getWines() {
            return wines;
        }

        public void setWines(List<Wine> wines) {
            this.wines = wines;
        }
    }

    @GetMapping("/api/recommends/vintage{top}")
    Object vintage(@RequestParam(name="top" , defaultValue = "10") int top) {
        var years = wineRepository.findBestYears(PageRequest.of(0 , top));

        return years
                .stream()
                .map( year -> new VintageResponse(String.valueOf(year[0])  , wineRepository.findAllByYearOrderByRatingDesc((Integer) year[0])) )
                .collect(Collectors.toList());
    }

}
