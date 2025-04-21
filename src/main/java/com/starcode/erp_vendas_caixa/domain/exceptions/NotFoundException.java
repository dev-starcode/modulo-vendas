package com.starcode.erp_vendas_caixa.domain.exceptions;

import java.util.Collections;
import java.util.List;

import com.starcode.erp_vendas_caixa.domain.aggregate.AggregateRoot;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String message, final List<Error> errors) {
        super(message, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregate,
            final Identifier id
    ) {
        final var anError = "%s com ID %s não foi encontrado".formatted(
                aggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.message(), List.of(error));
    }
}