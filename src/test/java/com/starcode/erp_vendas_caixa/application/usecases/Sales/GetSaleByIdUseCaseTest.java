package com.starcode.erp_vendas_caixa.application.usecases.Sales;

import com.starcode.erp_vendas_caixa.domain.dto.outputs.SaleOutputDTO;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSaleByIdUseCaseTest {

    @Mock
    private SaleGateway salesRepository;
    @InjectMocks
    private GetSaleByIdUseCase getSaleByIdUseCase;

    @Test
    @DisplayName("deve buscar uma venda pelo id")
    public void deveBuscarUmaVendaPeloId(){
        final var sale = Sale.create("userId", "client_id", "cashier_id", 50D, 0D, "paid");
        when(salesRepository.getSaleById(sale.getSaleId().getValue())).thenReturn(Optional.of(sale));
        final var result = this.getSaleByIdUseCase.execute(sale.getSaleId().getValue());
        assertNotNull(result);
        assertEquals(sale.getSaleId().getValue(), result.saleId());
    }

    @Test
    @DisplayName("deve retornar um not found quando não encontrado")
    public void deveRetornarUmNotFound(){
        final var result = assertThrows(NotFoundException.class, () -> {
            this.getSaleByIdUseCase.execute("not_found");
        });

        assertEquals("Venda não encontrada", result.getError());
    }
}