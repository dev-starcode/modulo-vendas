package com.starcode.erp_vendas_caixa.domain.value_objects;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public class Price extends ValueObject {
    private Double value;

    private Price(Double value) {
        this.value = value;
    }

    public static Price validate(Double price) {
        if (price == null) {
            throw DomainException.with(Error.create("UnprocessableEntity", "O preço não pode ser nulo."));
        }
        if (price < 0) {
            throw DomainException.with(Error.create("UnprocessableEntity","Preço deve ser maior ou igual a zero."));
        }
        if (price.isNaN() || price.isInfinite()) {
            throw DomainException.with(Error.create("UnprocessableEntity","Preço deve ser um número válido."));
        }
        return new Price(price);
    }

    public static Price restore(Double price){
        return new Price(price);
    }
    public void setValue(Double price) {
        if (price == null || price < 0) {
            throw DomainException.with(Error.create("UnprocessableEntity", "Preço deve ser maior que zero."));
        }
        if (price.isNaN() || price.isInfinite()) {
            throw DomainException.with(Error.create("UnprocessableEntity", "Preço deve ser um número válido."));
        }
        this.value = price;
    }
    public Double getValue() {
        return value;
    }
}
