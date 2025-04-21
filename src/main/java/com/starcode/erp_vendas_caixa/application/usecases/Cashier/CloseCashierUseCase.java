package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CloseCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

import java.util.List;

public class CloseCashierUseCase {
    private final CashierGateway cashierRepository;
    private final SaleGateway saleGateway;

    public CloseCashierUseCase(final CashierGateway cashierRepository,
                               final SaleGateway salesRepository) {
        this.cashierRepository = cashierRepository;
        this.saleGateway = salesRepository;
    }

    public CashierOutputDTO execute(CloseCashierInputDTO data) {
            final var cashier = this.cashierRepository.getCashierById(data.cashierId()).orElse(null);
            if(cashier == null) throw NotFoundException.with(Error.create("NotFoundError","Caixa não encontrado"));
            List<Sale> sales = this.saleGateway.getAllSalesOfCashier(data.cashierId());
            cashier.calculateClosingAmount(sales);
            cashier.addTotalSales(sales.size());
            cashier.close(data.userClosedId());
            this.cashierRepository.updateCashier(cashier);
            return CashierOutputDTO.create(cashier);
    }
}
