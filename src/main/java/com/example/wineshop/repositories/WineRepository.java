package com.example.wineshop.repositories;

import com.example.wineshop.models.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {

    List<Wine> findAllByOrderByRatingDesc(Pageable pageable);

    List<Wine> findAllByOrderByPriceDesc(Pageable pageable);

    @Query("select w from Wine w order by w.rating/w.price desc")
    List<Wine> findBestsRatingPrice(Pageable pageable);

    @Query("select w.year as year, avg(w.rating) as average from Wine w group by w.year order by average desc, w.year")
    List<Object[]> findBestYears(Pageable pageable);

    List<Wine> findAllByYearOrderByRatingDesc(Integer year , Pageable pageable);

    List<Wine> findAllByYearOrderByRatingDesc(Integer year);
}
