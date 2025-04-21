package com.starcode.erp_vendas_caixa.domain.entities.ProductSale;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.validation.Validator;

public class ProductSaleValidator extends Validator {
    private final ProductSale productSale;
    public ProductSaleValidator(final ProductSale productSale, final ValidationHandler handler) {
        super(handler);
        this.productSale = productSale;
    }

    @Override
    public void validate() {
        this.validateSaleId();
        this.validateProductId();
        this.validateProductName();
        if(this.productSale.getQuantity() <= 0) throw DomainException.with(Error.create("UnprocessableEntity","A quantidade de "+this.productSale.getProduct_name()+" deve ser maior que 0"));
    }

    private void validateSaleId(){
        if(this.productSale.getSale_id() == null) throw DomainException.with(Error.create("UnprocessableEntity", "sale_id não pode ser null"));
        if(this.productSale.getSale_id().trim().isEmpty()) throw DomainException.with(Error.create("UnprocessableEntity","sale_id deve ser maior que zero"));
    }
    private void validateProductId(){
        if(this.productSale.getProduct_id() == null) throw DomainException.with(Error.create("UnprocessableEntity","product_id não pode ser null"));
        if(this.productSale.getProduct_id().trim().isEmpty()) throw DomainException.with(Error.create("UnprocessableEntity","product_id deve ser maior que zero"));
    }
    private void validateProductName(){
        if(this.productSale.getProduct_name() == null) throw DomainException.with(Error.create("UnprocessableEntity","O nome do produto não pode ser null"));
        if(this.productSale.getProduct_name().trim().isEmpty()) throw DomainException.with(Error.create("UnprocessableEntity","O nome do produto não pode ser null"));
    }
}
