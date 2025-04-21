package com.starcode.erp_vendas_caixa.application.usecases.Cashier;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CreateCashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;

public class CreateCashierUseCase {

    private final CashierGateway cashierRepository;

    public CreateCashierUseCase(final CashierGateway cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    public CashierOutputDTO execute(final CreateCashierInputDTO data) throws RuntimeException {
        final var cashier = Cashier.create(data.userOpenedId(), data.openingAmount());
        cashier.validate(new ThrowsValidationHandler());
        this.cashierRepository.save(cashier);
        return CashierOutputDTO.create(cashier);
    }
}