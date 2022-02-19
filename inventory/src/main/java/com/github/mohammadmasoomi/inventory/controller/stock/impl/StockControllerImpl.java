package com.github.mohammadmasoomi.inventory.controller.stock.impl;

import com.github.mohammadmasoomi.inventory.configuration.mapper.StockMapper;
import com.github.mohammadmasoomi.inventory.controller.stock.StockController;
import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ws.rs.core.HttpHeaders;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class StockControllerImpl implements StockController {

    private final StockMapper stockMapper;
    private final StockService stockService;


    public StockControllerImpl(StockMapper stockMapper, StockService stockService) {
        this.stockMapper = stockMapper;
        this.stockService = stockService;
    }

/*    @Override
    public ModelAndView getByPageNumber(@PathVariable(name = "pageNumber") @NotNull long pageNumber) {
//            return new RedirectView("/api/stocks?page="+pageNumber);
        return new ModelAndView("redirect:/api/stocks?page=" + pageNumber  );

    }*/

    @Override
    public List<StockDTO> getAllByPageNumber(int page) {
        List<Stock> all = stockService.getAll(page);
        List<StockDTO> collect = all.stream().map(stockMapper::sourceToDestination).collect(Collectors.toList());
        collect.forEach(p -> {
            Link selfLink = linkTo(methodOn(StockControllerImpl.class).getById(p.getId())).withSelfRel();
            p.add(selfLink);
        });
        return collect;
    }

    @Override
    public StockDTO getById(long id) {
        Stock stock = stockService.getById(id);
        return stockMapper.sourceToDestination(stock);
    }


    @Override
    public ResponseEntity<Void> save(StockDTO stockDTO) {
        Stock stock = stockService.save(stockMapper.destinationToSource(stockDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stock.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.getPath()).build();
//        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Stock> updateStockPrice(long id, BigDecimal price) {
        Stock stock = stockService.updateStockPrice(id, price);
        return ResponseEntity.ok(stock);
    }

    @Override
    public ResponseEntity<String> deleteById(long id) {
        stockService.deleteById(id);
        return ResponseEntity.ok("Stock deleted");
    }

}