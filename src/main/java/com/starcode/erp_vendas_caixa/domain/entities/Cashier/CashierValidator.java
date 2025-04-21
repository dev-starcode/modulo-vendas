package com.starcode.erp_vendas_caixa.domain.entities.Cashier;

import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.validation.Validator;

public class CashierValidator extends Validator {
    final Cashier cashier;
    public CashierValidator(final Cashier cashier, final ValidationHandler handler) {
        super(handler);
        this.cashier = cashier;
    }

    @Override
    public void validate() {
        if(this.cashier.getUserOpenedId() == null)
            this.validationHandler().append(Error.create("UnprocessableEntity","user_opened_id não pode ser null."));

        if(this.cashier.getUserOpenedId().trim().isEmpty())
            this.validationHandler().append(Error.create("UnprocessableEntity","user_opened_id deve ser maior que zero."));
    }
}
