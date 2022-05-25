package com.example.wineshop;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WineController {
    private final WineRepository repository;

    WineController(WineRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/wine/{id}")
    Wine one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new WineNotFoundException(id));
    }

    @PutMapping("/api/wine/{id}")
    Wine replaceEmployee(@RequestBody Wine newWine, @PathVariable Long id) {

        return repository.findById(id)
                .map(wine -> {
                    wine.setWine(newWine.getWine());
                    //Modificar bodega (To do)
                    wine.setYear(newWine.getYear());
                    wine.setRating(newWine.getRating());
                    wine.setNum_reviews(newWine.getNum_reviews());
                    wine.setCountry(newWine.getCountry());
                    //Modificar region (To do)
                    wine.setPrice(newWine.getPrice());
                    //Modificar type (To do)
                    wine.setBody(newWine.getBody());
                    wine.setAcidity(newWine.getAcidity());

                    return repository.save(wine);
                })
                .orElseGet(() -> {
                    newWine.setId(id);
                    return repository.save(newWine);
                });
    }

    @DeleteMapping("/api/wine/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
