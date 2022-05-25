package com.example.wineshop.repositories;

import com.example.wineshop.models.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
}
