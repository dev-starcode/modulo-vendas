package com.starcode.erp_vendas_caixa.application.usecases.Cashier;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.OpenCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public class OpenCashierUseCase {

    private final CashierGateway cashierRepository;

    public OpenCashierUseCase(final CashierGateway cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    public CashierOutputDTO execute(final OpenCashierInputDTO data) throws RuntimeException {
            Cashier cashier = this.cashierRepository.getCashierById(data.cashierId()).orElse(null);
            if(cashier == null) throw NotFoundException.with(Error.create("NotFoundError","Caixa não encontrado"));
            cashier.open(data.userOpenedId());
            this.cashierRepository.updateCashier(cashier);
            return CashierOutputDTO.create(cashier);
    }
}
