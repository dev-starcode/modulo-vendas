package com.starcode.erp_vendas_caixa.application.usecases.Cashier;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public class GetCashierById {
    private final CashierGateway cashierRepository;
    public GetCashierById(final CashierGateway cashierRepository) {
        this.cashierRepository = cashierRepository;
    }
    public CashierOutputDTO execute(final String cashier_id) throws RuntimeException {
            Cashier cashier = this.cashierRepository.getCashierById(cashier_id).orElse(null);
            if(cashier == null) throw NotFoundException.with(Error.create("NotFoundError","Caixa não encontrado"));
            return CashierOutputDTO.create(cashier);
    }
}