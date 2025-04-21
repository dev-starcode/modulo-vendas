package com.starcode.erp_vendas_caixa.domain.entities.CashierMovements;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.validation.Validator;

public class CashierMovimentsValidator extends Validator {
    private final CashierMovements cashierMoviments;
    public CashierMovimentsValidator(final CashierMovements cashierMoviments, final ValidationHandler handler) {
        super(handler);
        this.cashierMoviments = cashierMoviments;
    }

    @Override
    public void validate() {
        this.validateCashierId();
        this.validateUserId();
    }
    private void validateCashierId(){
        if(this.cashierMoviments.getCashierId() == null) throw DomainException.with(Error.create("UnprocessableEntity", "cashier_id não pode ser null"));
        if(this.cashierMoviments.getCashierId().trim().isEmpty()) throw DomainException.with(Error.create("UnprocessableEntity", "cashier_id deve ser maior que zero"));
    }
    private void validateUserId(){
        if(this.cashierMoviments.getUserId() == null) throw DomainException.with(Error.create("UnprocessableEntity", "user_id não pode ser null"));
        if(this.cashierMoviments.getUserId().trim().isEmpty()) throw DomainException.with(Error.create("UnprocessableEntity", "user_id deve ser maior que zero"));
    }
}
