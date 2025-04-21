package com.starcode.erp_vendas_caixa.application.usecases.Sales;

import com.starcode.erp_vendas_caixa.domain.dto.outputs.SaleOutputDTO;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public class GetSaleByIdUseCase {

    private final SaleGateway saleGateway;

    public GetSaleByIdUseCase(SaleGateway saleGateway) {
        this.saleGateway = saleGateway;
    }

    public SaleOutputDTO execute(final String sale_id){
        final var sale = this.saleGateway.getSaleById(sale_id).orElse(null);
        if(sale == null) throw NotFoundException.with(new Error("NotFoundException", "Venda não encontrada"));
        return SaleOutputDTO.create(sale);
    }
}
