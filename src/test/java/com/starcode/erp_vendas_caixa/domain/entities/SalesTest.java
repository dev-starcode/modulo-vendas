package com.starcode.erp_vendas_caixa.domain.entities;

import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class SalesTest {

    @Test
    @DisplayName("Deve criar uma venda com valores válidos")
    void deveCriarUmaVendaComValoresValidos() {
        Sale sale = Sale.create("user123", "client123", "cashier123", 100.0, 0.0, "pending");
        sale.validate(new ThrowsValidationHandler());
        sale.calculateTotal();
        assertNotNull(sale);
        assertEquals(100.00, sale.getTotal());
        assertEquals("pending", sale.getStatus().toString());
    }
    @Test
    @DisplayName("Deve calcular o total com desconto")
    void deveCalcularOTotalComDesconto() {
        Sale sale = Sale.create("user123", "client123", "cashier123", 200.0, 50.0, "paid");
        sale.validate(new ThrowsValidationHandler());
        sale.calculateTotal();
        assertEquals(150.0, sale.getTotal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar venda com desconto maior que o preço parcial")
    void deleLancarUmaExceptioQuandoODescontoForMaiorQueOPrecoParcial() {
        DomainException exception = assertThrows(DomainException.class, () ->
                Sale.create("user123", "client123", "cashier123",
                        100D, 150D, "paid").validate(new ThrowsValidationHandler()));
        assertEquals("O desconto não pode ser maior que o preço parcial.", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve restaurar uma venda com valores válidos")
    void deveRestaurarUmaVendaComValoresValidos() {
        final var date = LocalDateTime.now();
        Sale sale = Sale.restore(UUID.randomUUID().toString(), "user123", "client123", "cashier123", 100.0, 10.0, 90.0,"paid", date);
        assertNotNull(sale);
        assertEquals(90.0, sale.getTotal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar venda com preço parcial nulo")
    void deveLancarUmaExcecaoCasoOPrecoParcialSejaNulo() {
        DomainException exception = assertThrows(DomainException.class, () -> Sale.create("user123", "client123", "cashier123", null, 10.0, "pending"));
        assertEquals("O preço não pode ser nulo.", exception.getErrors().getFirst().message());
    }
    @Test
    @DisplayName("Deve lançar exceção ao criar venda com preço parcial nulo")
    void deveLancarUmaExcecaoCasoOPrecoParcialSejaZero() {
        DomainException exception = assertThrows(DomainException.class, () -> Sale.create("user123", "client123", "cashier123", -5D, 10.0, "pending"));
        assertEquals("Preço deve ser maior ou igual a zero.", exception.getErrors().getFirst().message());
    }
    @Test
    @DisplayName("Deve completar a venda")
    void deveCompletarUmaVenda() {
        Sale sale = Sale.create("user123", "client123", "cashier123", 100.0, 10.0, "pending");
        sale.validate(new ThrowsValidationHandler());
        sale.paid();
        sale.calculateTotal();
        assertEquals("paid", sale.getStatus().toString());
        assertEquals(90.0, sale.getTotal());
    }
    @Test
    @DisplayName("Deve cancelar uma venda")
    void deveCancelarUmaVenda(){
        Sale sale = Sale.create("user123", "client123", "cashier123", 100.0, 10.0, "paid");
        sale.validate(new ThrowsValidationHandler());
        sale.cancel();
        assertEquals("canceled", sale.getStatus().toString());
    }

}