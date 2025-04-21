package com.starcode.erp_vendas_caixa.domain.value_objects;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @Test
    @DisplayName("deve criar um preço válido")
    void shouldCreateValidPrice() {
        Price price = Price.validate(100.0);
        assertEquals(100.0, price.getValue());
    }

    @Test
    @DisplayName("Não deve criar um preço caso seja menor que zero")
    void shouldThrowExceptionForNegativePrice() {
        final var exception = assertThrows(
                DomainException.class,
                () -> Price.validate(-10.0)
        );

        assertEquals("Preço deve ser maior ou igual a zero.", exception.getError());
    }

    @Test
    @DisplayName("não deve criar um preço caso seja null")
    void shouldThrowExceptionForNullPrice() {
        final var exception = assertThrows(DomainException.class, () -> Price.validate(null));
        assertEquals("O preço não pode ser nulo.", exception.getError());
    }

    @Test
    @DisplayName("não deve criar um preço caso seja um NaN")
    void shouldThrowExceptionForNaNPrice() {
        final var exception = assertThrows(DomainException.class, () -> Price.validate(Double.NaN));
        assertEquals("Preço deve ser um número válido.", exception.getError());
    }
    @Test
    @DisplayName("Deve restaurar um preço válido")
    void shouldRestoreValidPrice() {
        Price price = Price.restore(100.0);
        assertEquals(100.0, price.getValue());
    }

    @Test
    @DisplayName("Deve alterar o valor do preço para um valor válido")
    void shouldUpdatePriceValue(){
        Price price = Price.validate(50.0);
        price.setValue(70.0);
        assertEquals(70.0, price.getValue());
    }
    @Test
    @DisplayName("Não deve permitir definir um preço inválido (negativo)")
    void shouldThrowExceptionForNegativeSetValue() {
        Price price = Price.validate(50.0);
        final var exception = assertThrows(
                DomainException.class,
                () -> price.setValue(-5.0)
        );
        assertEquals("Preço deve ser maior que zero.", exception.getError());
    }

}