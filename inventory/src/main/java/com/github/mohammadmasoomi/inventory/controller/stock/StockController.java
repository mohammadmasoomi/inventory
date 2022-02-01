package com.github.mohammadmasoomi.inventory.controller.stock;

import com.github.mohammadmasoomi.inventory.configuration.mapper.StockMapper;
import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.core.ontology.HttpStatusCodes;
import com.github.mohammadmasoomi.inventory.core.ontology.PermissionOntology;
import com.github.mohammadmasoomi.inventory.core.validation.constraints.Amount;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import com.github.mohammadmasoomi.inventory.stock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping(path = "/api/stocks")
@RestController
@Validated
public class StockController {

    private final StockMapper stockMapper;
    private final StockService stockService;

    public StockController(StockMapper stockMapper, StockService stockService) {
        this.stockMapper = stockMapper;
        this.stockService = stockService;
    }

    @Operation(method = "GET", description = "get all stock by page number", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Stock page does not exist"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Page number validation error")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page"})
    @PreAuthorize("hasAuthority('" + PermissionOntology.GET_ALL_STOCK + "')")
    public List<StockDTO> getAllByPageNumber(@RequestParam("page") @Valid
                                             @Min(value = 0, message = "Page number must be great than zero") int page) {
        List<Stock> all = stockService.getAll(page);
        List<StockDTO> collect = all.stream().map(stockMapper::sourceToDestination).collect(Collectors.toList());
        collect.forEach(p -> {
            Link selfLink = linkTo(methodOn(StockController.class).getById(p.getId())).withSelfRel();
            p.add(selfLink);
        });
        return collect;
    }

    @Operation(method = "GET", description = "get stock by name", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Stock with this id does not exist"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Input data validation error, Business exception throw")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.GET_SOCK_BY_NAME + "')")
    public StockDTO getById(@PathVariable(name = "id") @NotNull @Min(value = 0, message = "id must be great than zero") long id) {
        Stock stock = stockService.getById(id);
        return stockMapper.sourceToDestination(stock);
    }


    @Operation(method = "POST", description = "save a stock", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "create successfully"),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Input data validation error"),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Stock with this name already exist")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.SAVE_STOCK + "')")
    public ResponseEntity<Void> save(@Valid @RequestBody StockDTO stockDTO) {
        Stock stock = stockService.save(stockMapper.destinationToSource(stockDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stock.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.getPath()).build();
//        return ResponseEntity.created(location).build();
    }

    @Operation(method = "PATCH", description = "Update stock price, id of stock is given in url, and price in request body (json format)",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Input data validation error")
    })
    @PatchMapping(value = "{id}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.UPDATE_STOCK_PRICE + "')")
    public ResponseEntity<Stock> updateStockPrice(@PathVariable(name = "id") @NotNull @Min(value = 0, message = "id must be great than zero") long id,
                                                  @PathVariable(name = "price") @NotNull @Amount BigDecimal price) {
        Stock stock = stockService.updateStockPrice(id, price);
        return ResponseEntity.ok(stock);
    }

    @Operation(method = "DELETE", description = "Delete a stock by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Input data validation error or business exception"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Stock with this id does not exist")
    })
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('" + PermissionOntology.DELETE_STOCK + "')")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") @NotNull @Min(value = 0, message = "id must be great than zero") long id) {
        stockService.deleteById(id);
        return ResponseEntity.ok("Stock deleted");
    }

}