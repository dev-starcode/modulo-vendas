package com.starcode.erp_vendas_caixa.application.usecases.Sales;

import com.starcode.erp_vendas_caixa.application.factory.CashierMovementFactory;
import com.starcode.erp_vendas_caixa.application.factory.SaleFactory;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.SaleOutputDTO;
import com.starcode.erp_vendas_caixa.domain.exceptions.ConflictException;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierMovementsGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.ProductSaleGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateSaleInputDTO;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;

public class CreateSaleUseCase {
    private final SaleGateway salesRepository;
    private final ProductSaleGateway saleProductRepository;
    private final CashierMovementsGateway cashierMovementsGateway;
    private final CashierGateway cashierRepository;

    public CreateSaleUseCase(final SaleGateway salesRepository,
                             final ProductSaleGateway saleProductRepository,
                             final CashierMovementsGateway cashierMovimentsRepository,
                             final CashierGateway cashierRepository
) {
        this.salesRepository = salesRepository;
        this.saleProductRepository = saleProductRepository;
        this.cashierMovementsGateway = cashierMovimentsRepository;
        this.cashierRepository = cashierRepository;
    }

    public SaleOutputDTO execute(final CreateSaleInputDTO data) {
        final var cashier = this.cashierRepository.getCashierById(data.cashierId()).orElse(null);
        if(cashier == null) throw NotFoundException.with(Error.create("NotFoundError","O caixa não existe"));
        if(cashier.getStatus().toString().equals("closed"))
            throw ConflictException.with(Error.create("Conflict","O caixa esta fechado"));
        final var sale = Sale.create(
                    data.userId(),
                    data.clientId(),
                    data.cashierId(),
                    data.partialPrice(),
                    data.discount(),
                    data.status()
            );
        sale.validate(new ThrowsValidationHandler());
        sale.calculateTotal();
        final var saleProducts = SaleFactory.createProductSales(sale, data.productsSale());
        final var cashierMovements = CashierMovementFactory.create(data, sale.getTotal());
        this.salesRepository.save(sale);
        saleProducts.forEach(this.saleProductRepository::save);
        this.cashierMovementsGateway.save(cashierMovements);
        return SaleOutputDTO.create(sale);
    }
}

