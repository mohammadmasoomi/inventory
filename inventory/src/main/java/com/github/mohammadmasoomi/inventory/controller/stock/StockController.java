package com.github.mohammadmasoomi.inventory.controller.stock;

import com.github.mohammadmasoomi.inventory.controller.stock.dto.StockDTO;
import com.github.mohammadmasoomi.inventory.core.ontology.HttpStatusCodes;
import com.github.mohammadmasoomi.inventory.core.ontology.PermissionOntology;
import com.github.mohammadmasoomi.inventory.core.validation.constraints.Amount;
import com.github.mohammadmasoomi.inventory.stock.entity.Stock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@RequestMapping(path = "/api/stocks")
@Validated
public interface StockController {

    /*@GetMapping("/getByPageNumber/{pageNumber}")
    ModelAndView getByPageNumber(@PathVariable(name = "pageNumber") @NotNull long pageNumber);*/

    @Operation(method = "GET", description = "get all stock by page number", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Stock page does not exist"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Page number validation error")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page"})
    @PreAuthorize("hasAuthority('" + PermissionOntology.GET_ALL_STOCK + "')")
    List<StockDTO> getAllByPageNumber(@RequestParam("page") @Valid
                                      @Min(value = 0, message = "Page number must be great than zero") int page);

    @Operation(method = "GET", description = "get stock by name", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Stock with this id does not exist"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Input data validation error, Business exception throw")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.GET_SOCK_BY_NAME + "')")
    StockDTO getById(@PathVariable(name = "id") @NotNull @Min(value = 0, message = "id must be great than zero") long id);


    @Operation(method = "POST", description = "save a stock", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "create successfully"),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Input data validation error"),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Stock with this name already exist")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.SAVE_STOCK + "')")
    ResponseEntity<Void> save(@Valid @RequestBody StockDTO stockDTO);

    @Operation(method = "PATCH", description = "Update stock price, id of stock is given in url, and price in request body (json format)",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Input data validation error")
    })
    @PatchMapping(value = "{id}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.UPDATE_STOCK_PRICE + "')")
    ResponseEntity<Stock> updateStockPrice(@PathVariable(name = "id") @NotNull @Min(value = 0, message = "id must be great than zero") long id,
                                           @PathVariable(name = "price") @NotNull @Amount BigDecimal price);

    @Operation(method = "DELETE", description = "Delete a stock by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "success"),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Input data validation error or business exception"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Stock with this id does not exist")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAuthority('" + PermissionOntology.DELETE_STOCK + "')")
    ResponseEntity<String> deleteById(@PathVariable(name = "id") @NotNull @Min(value = 0, message = "id must be great than zero") long id);

}