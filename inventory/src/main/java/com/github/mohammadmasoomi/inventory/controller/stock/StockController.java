package com.github.mohammadmasoomi.inventory.controller.stock;

import com.github.mohammadmasoomi.inventory.InventoryApplication;
import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.core.ontology.PermissionOntology;
import com.github.mohammadmasoomi.inventory.core.validation.constraints.Amount;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockAlreadyExistException;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockDoesNotExistException;
import com.github.mohammadmasoomi.inventory.stock.service.exception.StockPageDoesNotExistException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "/api")
@RestController
@Validated
public class StockController {

    private static final InventoryApplication.StockMapper STOCK_MAPPER = Mappers.getMapper(InventoryApplication.StockMapper.class);
    private static final InventoryApplication.StockDTOMapper STOCK_DTO_MAPPER = Mappers.getMapper(InventoryApplication.StockDTOMapper.class);

    @Autowired
    private StockService stockService;


    @Operation(method = "GET", description = "get all stock by page number")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "Stock page does not exist"),
            @ApiResponse(responseCode = "404", description = "Page number validation error")})
    @GetMapping(path = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE, params = {"page"})
    @PreAuthorize("hasAuthority('" + PermissionOntology.GET_ALL_STOCK + "')")
    public List<StockDTO> getAllByPageNumber(@RequestParam("page") @Valid
                                             @Min(value = 0, message = "Page number must be great than zero") int page) throws StockPageDoesNotExistException {
        List<Stock> all = stockService.getAll(page);
        return all.stream().map(STOCK_DTO_MAPPER::sourceToDestination).collect(Collectors.toList());
    }

    @Operation(method = "GET", description = "get stock by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "Input data validation error, Business exception throw")
    })
    @GetMapping(path = "/stocks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.GET_SOCK_BY_NAME + "')")
    public StockDTO getByName(@PathVariable(name = "id") @NotNull long id) throws StockDoesNotExistException {
        Stock stock = stockService.getById(id);
        return STOCK_DTO_MAPPER.sourceToDestination(stock);
    }


    @Operation(method = "POST", description = "save a stock")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "create successfully"),
            @ApiResponse(responseCode = "400", description = "Input data validation error"),
            @ApiResponse(responseCode = "409", description = "Stock with this name already exist")
    })
    @PostMapping(path = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.SAVE_STOCK + "')")
    public ResponseEntity<Void> save(@Valid @RequestBody StockDTO stockDTO) throws StockAlreadyExistException {
        Stock newStock = stockService.save(STOCK_MAPPER.destinationToSource(stockDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newStock.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.getPath()).build();
//        return ResponseEntity.created(location).build();
    }

    @Operation(method = "PATCH", description = "Update stock price, id of stock is given in url, and price in request body (json format)")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "success"),
            @ApiResponse(responseCode = "400", description = "Input data validation error")
    })
    @PatchMapping(value = "/stocks/{id}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.UPDATE_STOCK_PRICE + "')")
    public ResponseEntity<Stock> updateStockPrice(@PathVariable(name = "id") @NotNull long id,
                                                  @NotNull @Amount @PathVariable(name = "price") BigDecimal price) throws StockDoesNotExistException {
        Stock stock = stockService.updateStockPrice(id, price);
        return ResponseEntity.ok(stock);
    }

    @Operation(method = "DELETE", description = "Delete a stock by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "Input data validation error or business exception"),
            @ApiResponse(responseCode = "404", description = "Stock with this id does not exist")
    })
    @DeleteMapping(path = "/stocks/{id}")
    @PreAuthorize("hasAuthority('" + PermissionOntology.DELETE_STOCK + "')")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") @NotNull long id) throws StockDoesNotExistException {
        stockService.deleteById(id);
        return ResponseEntity.ok("Stock deleted");
    }

}