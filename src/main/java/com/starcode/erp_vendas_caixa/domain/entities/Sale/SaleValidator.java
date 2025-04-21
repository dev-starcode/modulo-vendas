package com.starcode.erp_vendas_caixa.domain.entities.Sale;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.validation.Validator;

public class SaleValidator extends Validator {

    private final Sale sale;
    protected SaleValidator(final Sale sale, final ValidationHandler handler) {
        super(handler);
        this.sale = sale;
    }

    @Override
    public void validate() {
        this.validateUserId();
//        this.validateClientId();
        this.validateCashierId();
        if (this.sale.getDiscount() .getValue()> this.sale.getPartialPrice().getValue()){
            throw  DomainException.with(Error.create("UnprocessableEntity","O desconto não pode ser maior que o preço parcial."));
        }
    }

    private void validateUserId(){
        if(this.sale.getUserId() == null) this.validationHandler().append(Error.create("UnprocessableEntity","user_id não pode ser null"));
        if(this.sale.getUserId().trim().isEmpty()) this.validationHandler().append(Error.create("UnprocessableEntity","user_id deve ser maior que zero"));
    }
    private void validateClientId(){
        if(this.sale.getClientId() == null) this.validationHandler().append(Error.create("UnprocessableEntity","cliente_id não pode ser null"));
        if(this.sale.getClientId().trim().isEmpty()) this.validationHandler().append(Error.create("UnprocessableEntity","cliente_id deve ser maior que zero"));
    }
    private void validateCashierId(){
        if(this.sale.getCashierId() == null) this.validationHandler().append(Error.create("UnprocessableEntity","cashier_id não pode ser null"));
        if(this.sale.getCashierId().trim().isEmpty()) this.validationHandler().append(Error.create("UnprocessableEntity","cashier_id deve ser maior que zero"));

    }
}
