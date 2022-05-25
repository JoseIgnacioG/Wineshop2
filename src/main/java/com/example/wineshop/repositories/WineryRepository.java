package com.example.wineshop.repositories;

import com.example.wineshop.models.Winery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineryRepository extends JpaRepository<Winery, Long> {
}