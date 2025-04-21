package com.starcode.erp_vendas_caixa.infra.api;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CashierApiOutput;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CloseCashierApiInput;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CreateCashierApiInput;
import com.starcode.erp_vendas_caixa.infra.cashier.models.OpenCashierApiInput;
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

@RequestMapping(value = "/cashiers")
@Tag(name = "Caixas")
public interface CashierAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar um novo caixa", method = "post")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Criado com sucesso",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CashierApiOutput.class))),
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

    ResponseEntity<?> createCashier(@RequestBody @Valid CreateCashierApiInput input);

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Bucar todos os caixas", method = "get")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Lista de caixas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pagination.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    ResponseEntity<?> getAllCashiers(
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "parPage", required = false, defaultValue = "10") final int parPage,
            @RequestParam(name = "term", required = false, defaultValue = "") final String search,
            @RequestParam(name = "sort", required = false, defaultValue = "openedAt") final String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") final String direction
    );
    @GetMapping(
            value = "/{cashier_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Caixas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CashierOutputDTO.class))),
            @ApiResponse(
                    responseCode = "404", description = "Não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))),
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
    @Operation(summary = "Bucar caixa pelo id", method = "get")
    ResponseEntity<?> getCashierById(
            @PathVariable("cashier_id") final String cashier_id
    );
    @PostMapping(
            value = "/{cashier_id}/close",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Fechar Caixa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CashierOutputDTO.class))),
            @ApiResponse(
                    responseCode = "404", description = "Não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    @Operation(summary = "Fechar um caixa", method = "post")
    ResponseEntity<?> closeCashier(
            @PathVariable("cashier_id") final String cashier_id,
            @RequestBody @Valid final CloseCashierApiInput data
    );

    @PostMapping(
            value = "/{cashier_id}/open",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Fechar Caixa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CashierOutputDTO.class))),
            @ApiResponse(
                    responseCode = "404", description = "Não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    @Operation(summary = "Abirir um caixa", method = "post")
    ResponseEntity<?> abrirCaixa(
            @PathVariable("cashier_id") final String cashier_id,
            @RequestBody @Valid final OpenCashierApiInput data
    );
}