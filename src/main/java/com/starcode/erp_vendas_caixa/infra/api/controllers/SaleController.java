package com.starcode.erp_vendas_caixa.infra.api.controllers;

import com.starcode.erp_vendas_caixa.application.usecases.Sales.CreateSaleUseCase;
import com.starcode.erp_vendas_caixa.application.usecases.Sales.GetAllSalesByCashierUseCase;
import com.starcode.erp_vendas_caixa.application.usecases.Sales.GetSaleByIdUseCase;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateSaleInputDTO;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.ProductSaleDTO;
import com.starcode.erp_vendas_caixa.infra.api.SaleAPI;
import com.starcode.erp_vendas_caixa.infra.sales.models.CreateSaleInputAPI;
import com.starcode.erp_vendas_caixa.infra.sales.models.SaleApiOutput;
import com.starcode.erp_vendas_caixa.infra.sales.models.SalePaginationApiOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class SaleController implements SaleAPI {

    private final CreateSaleUseCase createSaleUseCase;
    private final GetAllSalesByCashierUseCase getAllSalesByCashierUseCase;
    private final GetSaleByIdUseCase getSaleByIdUseCase;

    public SaleController(CreateSaleUseCase createSaleUseCase, GetAllSalesByCashierUseCase getAllSalesByCashierUseCase, GetSaleByIdUseCase getSaleByIdUseCase) {
        this.createSaleUseCase = createSaleUseCase;
        this.getAllSalesByCashierUseCase = getAllSalesByCashierUseCase;
        this.getSaleByIdUseCase = getSaleByIdUseCase;
    }

    @Override
    public ResponseEntity<?> createSale(CreateSaleInputAPI data) {
        final var products = data.products()
                .stream().map(product ->
                        new ProductSaleDTO(product.productId(),
                                product.productName(),
                                product.priceCost(),
                                product.priceSale(),
                                product.quantity())).toList();

        final var input = new CreateSaleInputDTO(
                data.userId(),
                data.clientId(),
                data.cashierId(),
                data.partialPrice(),
                data.discount(),
                data.status(),
                data.reason(),
                products);
        final var output = this.createSaleUseCase.execute(input);
        final var response = new SaleApiOutput("Venda criada com sucesso", output);
        return ResponseEntity.created(URI.create("/sales/" + output.saleId())).body(response);
    }

    @Override
    public ResponseEntity<?> getSaleById(final String sale_id) {
        final var output = this.getSaleByIdUseCase.execute(sale_id);
        final var response = new SaleApiOutput("Venda encontrada", output);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getSaleByCashierId(
            String cashier_id,
            int page,
            int parPage,
            String search,
            String sort,
            String direction) {
        final var search_query = new SearchQuery(page, parPage, search, sort, direction);
        final var output = this.getAllSalesByCashierUseCase.execute(search_query, cashier_id);
        final var response = new SalePaginationApiOutput("Lista de vendas", output);
        return ResponseEntity.ok(response);
    }
}