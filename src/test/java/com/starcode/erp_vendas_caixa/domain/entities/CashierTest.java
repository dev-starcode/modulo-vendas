package com.starcode.erp_vendas_caixa.domain.entities;

import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashierTest {

    private Cashier cashier;

    @BeforeEach
    void setUp(){
        cashier = Cashier.create("user123", 100.00);
    }

    @Test
    @DisplayName("Deve criar um caixa com os valores corretos")
    void deveCriarUmCaixaComOsValoresCorretos() {

        final var cashier = Cashier.create("user123", 100.00);
        cashier.validate(new ThrowsValidationHandler());
        assertNotNull(cashier);
        assertNotNull(cashier.getCashierId());
        assertEquals("user123", cashier.getUserOpenedId());
        assertEquals(Cashier.Status.opened, cashier.getStatus());
        assertNotNull(cashier.getOpenedAt());
        assertNull(cashier.getClosedAt());
        assertEquals(0, cashier.getTotalSales());
        assertEquals(100.00, cashier.getOpeningAmount().getValue());
        assertEquals(0.0, cashier.getClosingAmount().getValue());
    }

    @Test
    @DisplayName("Deve lançar um erro ao passar o user_opened_id null")
    void deveLancarUmDomainExceptionCasoOUserOpenedIdSejaNull() {
        final var exception = assertThrows(DomainException.class, () -> Cashier.create(null, 100.00).validate(new ThrowsValidationHandler()));
        assertNotNull(exception.getMessage());
        assertEquals("user_opened_id não pode ser null.", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve lançar um erro ao passar o user_opened_id null")
    void deveLancarUmDomainExceptionCasoOUserOpenedIdSejaNada() {
        final var exception = assertThrows(DomainException.class, () -> Cashier.create("", 100.00).validate(new ThrowsValidationHandler()));
        assertNotNull(exception.getMessage());
        assertEquals("user_opened_id deve ser maior que zero.", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve calcular o valor de fechamento")
    void daveCalcularOValorDeFechamento() {
        List<Sale> sales = new ArrayList<>();
        sales.add(Sale.create("venda1", "usuario1", "cliente1", 100D, 0D, "paid"));
        sales.add(Sale.create("venda2", "usuario2", "cliente2", 150D, 30D, "paid"));
        sales.add(Sale.create("venda3", "usuario3", "cliente3", 25D, 12.5D, "paid"));
        for(Sale sale : sales){
           sale.calculateTotal();
        }

        Cashier cashier = Cashier.create("user", 100D);
        cashier.calculateClosingAmount(sales);
        assertEquals(332.50, cashier.getClosingAmount().getValue());
    }
    @Test
    @DisplayName("Deve fechar um caixa corretamente")
    void deveFecharOCaixaComSucesso() {
        cashier.close("user456");
        assertEquals(Cashier.Status.closed, cashier.getStatus());
        assertEquals("user456", cashier.getUserClosedId());
        assertNotNull(cashier.getClosedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar abrir um caixa já aberto")
    void deveLancarUmExceptionCasoOCaixaJaEstajaAberto() {

        DomainException exception = assertThrows(DomainException.class, () -> cashier.open("user_opened_id"));
        assertEquals("O caixa já está aberto.", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar fechar um caixa já fechado")
    void deveLancarUmExceptionCasoOCaixaJaEstajaFehcado() {
        cashier.close("user456");

        DomainException exception = assertThrows(DomainException.class, () -> cashier.close("user789"));

        assertEquals("O caixa já está fechado.", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve adicionar uma venda corretamente")
    void deveAdicionarUmaVendaComSucesso() {
        cashier.addSale();

        assertEquals(1, cashier.getTotalSales());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar adicionar venda em caixa fechado")
    void deveLancarUmaExceptionQuandoAdicionarUmaVendaComCaixaFechado() {
        cashier.close("user456");

        DomainException exception = assertThrows(DomainException.class, () -> {
            cashier.addSale();
        });

        assertEquals("Não é possível adicionar vendas a um caixa fechado.", exception.getErrors().getFirst().message());
    }
}