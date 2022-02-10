package com.github.mohammadmasoomi.inventory.stock.service;

import com.github.mohammadmasoomi.inventory.core.repository.base.SearchCriteria;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.repository.StockRepository;
import com.github.mohammadmasoomi.inventory.stock.repository.StockSpecification;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockAlreadyExistException;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockDoesNotExistException;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockNotFoundByCriteriaException;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockPageDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequestScope
public class StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    @Value("${inventory.stock.pageSize}")
    private String pageSize;

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * This method persists given stock
     *
     * @param stock validated input for insert to database
     * @throws StockAlreadyExistException this exception throw when duplicated stock by name
     *                                    this method persis a stock
     */
    public Stock save(Stock stock) {

        //control existence of stock by name
        boolean existenceByName = stockRepository.controlExistenceByName(stock.getName());
        if (existenceByName) {
            LOGGER.info("Stock with name: " + stock.getName() + " already exist");
            throw new StockAlreadyExistException();
        }

        stockRepository.save(stock);
        return stock;
    }

    /**
     * This method updates the stock price
     *
     * @param id           of stock that must be updated
     * @param currentPrice new price of stock
     * @throws StockDoesNotExistException when stock does not exist with the specified value
     */
    public Stock updateStockPrice(long id, BigDecimal currentPrice) {

        //control existence of stock by name
        Optional<Stock> result = stockRepository.findById(id);
        if (result.isEmpty()) {
            LOGGER.info("Stock with id: " + id + " does not exist");
            throw new StockDoesNotExistException();
        }
        Stock stock = result.get();
        stock.setCurrentPrice(currentPrice);
        stockRepository.save(stock);
        return stock;
    }

    /**
     * This method returns all stock
     *
     * @param pageNumber number of page
     * @throws StockPageDoesNotExistException when stock page does not exist
     */
    public List<Stock> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Integer.parseInt(pageSize));
        Page<Stock> all = stockRepository.findAll(pageable);
        if (pageNumber > all.getTotalPages()) {
            throw new StockPageDoesNotExistException();
        }
        return all.getContent();
    }

    /**
     * This method returns a stock by specific name
     *
     * @param id is our search parameter
     * @return a stock who its name matches with method input
     * @throws StockDoesNotExistException when stock does not exist
     */
    public Stock getById(long id) {
        Optional<Stock> result = stockRepository.findById(id);
        if (result.isEmpty()) {
            LOGGER.info("Stock with id: " + id + " does not exist");
            throw new StockDoesNotExistException();
        }
        return result.get();
    }

    /**
     * This method deletes stock by name
     *
     * @param id of stock that must br deleted
     * @throws StockDoesNotExistException when stock does not exist
     */
    public void deleteById(long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isEmpty()) {
            LOGGER.info("Stock with id: " + id + " does not exist");
            throw new StockDoesNotExistException();
        }
        stockRepository.delete(stock.get());
    }

    /**
     * search on stock by criteria
     *
     * @param searchCriteria criteria for search stock
     * @throws StockNotFoundByCriteriaException when stock not found
     */
    public List<Stock> getAll(SearchCriteria searchCriteria) {
        StockSpecification stockSpecification = new StockSpecification(searchCriteria);
        List<Stock> all = stockRepository.findAll(stockSpecification);
        if (all.isEmpty()) {
            LOGGER.info("Stock with searchCriteria: " + searchCriteria + " does not exist");
            throw new StockNotFoundByCriteriaException();
        }
        return all;
    }

    /**
     * search on stock by criteria list with and operation
     *
     * @param searchCriteriaList criteria list for search stock
     * @throws StockNotFoundByCriteriaException when stock not found
     */
    public List<Stock> getAll(List<SearchCriteria> searchCriteriaList) {
        Specification<Stock> where = null;
        for (int index = 0; index < searchCriteriaList.size(); index++) {
            if (index == 0) {
                where = Specification.where(new StockSpecification(searchCriteriaList.get(index)));
            } else {
                where.and(new StockSpecification(searchCriteriaList.get(index)));
            }
        }
        List<Stock> all = stockRepository.findAll(where);
        if (all.isEmpty()) {
            LOGGER.info("Stock with searchCriteriaList: " + searchCriteriaList + " does not exist");
            throw new StockNotFoundByCriteriaException();
        }
        return all;
    }

}