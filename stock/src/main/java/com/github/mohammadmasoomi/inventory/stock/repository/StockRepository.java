package com.github.mohammadmasoomi.inventory.stock.repository;

import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {

    @Query("select case when count(s) > 0 " +
            "then true else false end from Stock s " +
            "where s.name = :name")
    boolean controlExistenceByName(@Param("name") String name);

    Stock findByName(String name);

    @Modifying
    void deleteByName(String name);
}
