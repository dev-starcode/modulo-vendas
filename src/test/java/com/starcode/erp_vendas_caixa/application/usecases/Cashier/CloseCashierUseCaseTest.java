package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.dto.inputs.CloseCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CloseCashierUseCaseTest {
    @InjectMocks
    private CloseCashierUseCase closeCashierUseCase;
    @Mock
    private CashierGateway cashierRepository;
    @Mock
    private SaleGateway salesRepository;


    @Test
    @DisplayName("Deve fechar um caixa corretamente")
    public void deveFecharUmCaixaCorretament(){
        final var cashier = Cashier.create("user_opened_id", 100D);
        final var sales = List.of(
                Sale.restore("sale_id1", "user_id", "client_id","cashier_id",50.0, 0.0, 50D, "paid", LocalDateTime.now()),
                Sale.restore("sale_id1", "user_id", "client_id","cashier_id",100D, 30D, 70D, "paid", LocalDateTime.now()));

        final var cashier_id = "cashier_id";
        final var user_closed_id =  "user_closed_id";
        final var input = new CloseCashierInputDTO(cashier_id, user_closed_id);

        when(cashierRepository.getCashierById("cashier_id")).thenReturn(Optional.of(cashier));
        when(salesRepository.getAllSalesOfCashier("cashier_id")).thenReturn(sales);

        final var output = this.closeCashierUseCase.execute(input);
        assertEquals("closed", output.status());
        assertEquals(2, output.totalSales());
        assertEquals(220, output.closingAmount());

    }

    @Test
    @DisplayName("Deve lançar uma exception caso o caixa esteja fechado")
    public void deveLancarUmaExcecaoQuandoNaoEncotrarOCaixa(){

        final var cashier_id = "cashier";
        final var user_closed_id =  "user_closed_id";
        final var input = new CloseCashierInputDTO(cashier_id, user_closed_id);

        when(cashierRepository.getCashierById(cashier_id)).thenReturn(Optional.empty());

        final var output = assertThrows(NotFoundException.class,
                () -> this.closeCashierUseCase.execute(input));
        assertEquals("Caixa não encontrado", output.getErrors().getFirst().message());
    }
    @Test
    @DisplayName("Deve calcular sem vendas")
    public void deveFecharUmCaixaSemVendasRegistradas(){
        final var cashier = Cashier.create("user_opened_id", 100D);

        final var cashier_id = "cashier_id";
        final var user_closed_id =  "user_closed_id";
        final var input = new CloseCashierInputDTO(cashier_id, user_closed_id);

        when(cashierRepository.getCashierById("cashier_id")).thenReturn(Optional.of(cashier));
        when(salesRepository.getAllSalesOfCashier("cashier_id")).thenReturn(List.of());
        final var output = this.closeCashierUseCase.execute(input);
        assertEquals("closed", output.status());
        assertEquals(0, output.totalSales());
        assertEquals(100, output.closingAmount());

    }
}