package com.starcode.erp_vendas_caixa.domain.validation.handler;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create(){
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error error){
        return (Notification) new Notification(new ArrayList<>()).append(error);
    }

    @Override
    public ValidationHandler append(final Error error) {
        this.errors.add(error);
        return this;
    }

//    @Override
//    public ValidationHandler append(ValidationHandler handler) {
//        this.errors.addAll(handler.getErrors());
//        return this;
//    }

    @Override
    public ValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (final DomainException e){
            this.errors.addAll(e.getErrors());
        } catch (final Throwable t){
            this.errors.add(Error.create("", t.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.getErrors();
    }

}
