package com.github.mohammadmasoomi.inventory.stock.repository;

import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends PagingAndSortingRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {

    @Query("select case when count(s) > 0 " +
            "then true else false end from Stock s " +
            "where s.name = :name")
    boolean controlExistenceByName(@Param("name") String name);
}
