package com.starcode.erp_vendas_caixa.infra.api;

import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.infra.sales.models.CreateSaleInputAPI;
import com.starcode.erp_vendas_caixa.infra.sales.models.SaleApiOutput;
import com.starcode.erp_vendas_caixa.infra.sales.models.SalePaginationApiOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/sales")
@Tag(name = "Vendas")
public interface SaleAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar uma nova venda", method = "post")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaleApiOutput.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "Não processável",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Pedido ruim",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    ResponseEntity<?> createSale(@RequestBody @Valid CreateSaleInputAPI input);

    @GetMapping(
            value = "/{sale_id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar vendas pelo id", method = "get")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Lista de vendas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaleApiOutput.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    ResponseEntity<?> getSaleById(
            @PathVariable("sale_id") final String sale_id
    );

    @GetMapping(
            value = "/cashier{cashier_id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar vendas pelo caixa", method = "get")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Lista de vendas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalePaginationApiOutput.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    ResponseEntity<?> getSaleByCashierId(
            @PathVariable("cashier_id") final String cashier_id,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "parPage", required = false, defaultValue = "10") final int parPage,
            @RequestParam(name = "term", required = false, defaultValue = "") final String search,
            @RequestParam(name = "sort", required = false, defaultValue = "saleDate") final String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") final String direction
    );
}
