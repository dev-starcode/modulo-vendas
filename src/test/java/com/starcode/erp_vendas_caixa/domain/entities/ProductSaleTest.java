package com.starcode.erp_vendas_caixa.domain.entities;

import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductSaleTest {

    @Test
    @DisplayName("Deve criar um produto de venda válido")
    void deveCriarUmProdutoValido() {
        ProductSale saleProduct = ProductSale.create(
                "sale123", "product123", "Produto Teste", 10.0, 20.0, 2
        );

        assertNotNull(saleProduct);
        assertNotNull(saleProduct.getSale_product_id());
        assertEquals("sale123", saleProduct.getSale_id());
        assertEquals("product123", saleProduct.getProduct_id());
        assertEquals("Produto Teste", saleProduct.getProduct_name());
        assertEquals(10.0, saleProduct.getPrice_coust().getValue());
        assertEquals(20.0, saleProduct.getPrice_sale().getValue());
        assertEquals(2, saleProduct.getQuantity());
        assertEquals(0.0, saleProduct.getTotal_price().getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto de venda com sale_id nulo")
    void deveLancarUmExcecaoCasoOSaleIdSejaNull() {
        final var exception = assertThrows(DomainException.class, () -> ProductSale.create(null, "product123", "Produto Teste", 10.0, 20.0, 2).validate(new ThrowsValidationHandler()));

        assertEquals("sale_id não pode ser null", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto de venda com product_id nulo")
    void deveLancarUmExcecaoCasoOProductIdSejaNull() {
        final var exception = assertThrows(DomainException.class, () -> ProductSale.create("sale123", null, "Produto Teste", 10.0, 20.0, 2).validate(new ThrowsValidationHandler()));

        assertEquals("product_id não pode ser null", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto de venda com nome nulo")
    void shouldThrowExceptionWhenProductNameIsNull() {
        final var exception = assertThrows(DomainException.class, () -> ProductSale.create("sale123", "product123", null, 10.0, 20.0, 2).validate(new ThrowsValidationHandler()));

        assertEquals("O nome do produto não pode ser null", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto de venda com quantidade zero ou negativa")
    void shouldThrowExceptionWhenQuantityIsZeroOrNegative() {
        final var exception = assertThrows(DomainException.class, () -> ProductSale.create("sale123", "product123", "Produto Teste", 10.0, 20.0, 0).validate(new ThrowsValidationHandler()));

        assertEquals("A quantidade de Produto Teste deve ser maior que 0", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve restaurar um produto de venda válido")
    void shouldRestoreValidSaleProduct() {
        final var saleProduct = ProductSale.restore(
                "saleProduct123", "sale123", "product123", "Produto Restaurado", 15.0, 30.0, 3, 90.0
        );

        assertNotNull(saleProduct);
        assertEquals("saleProduct123", saleProduct.getSale_product_id().getValue());
        assertEquals("sale123", saleProduct.getSale_id());
        assertEquals("product123", saleProduct.getProduct_id());
        assertEquals("Produto Restaurado", saleProduct.getProduct_name());
        assertEquals(15.0, saleProduct.getPrice_coust().getValue());
        assertEquals(30.0, saleProduct.getPrice_sale().getValue());
        assertEquals(3, saleProduct.getQuantity());
        assertEquals(90.0, saleProduct.getTotal_price().getValue());
    }

    @Test
    @DisplayName("Deve calcular o preço total corretamente")
    void shouldCalculateTotalPriceCorrectly() {
        final var saleProduct = ProductSale.create(
                "sale123", "product123", "Produto Teste", 10.0, 25.0, 4
        );

        saleProduct.calculateTotal();
        assertEquals(100.0, saleProduct.getTotal_price().getValue());
    }
}