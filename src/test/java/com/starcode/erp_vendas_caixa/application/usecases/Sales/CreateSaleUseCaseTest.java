package com.starcode.erp_vendas_caixa.application.usecases.Sales;

import com.starcode.erp_vendas_caixa.application.factory.CashierMovementFactory;
import com.starcode.erp_vendas_caixa.application.factory.SaleFactory;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateSaleInputDTO;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.ProductSaleDTO;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;
import com.starcode.erp_vendas_caixa.domain.exceptions.ConflictException;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSaleUseCaseTest {

    @Mock private SaleGateway salesRepository;
    @Mock private ProductSaleGateway saleProductRepository;
    @Mock private CashierMovementsGateway cashierMovementsRepository;
    @Mock private CashierGateway cashierRepository;
    @Mock private SaleFactory saleFactory;
    @Mock private CashierMovementFactory movementFactory;

    @InjectMocks
    private CreateSaleUseCase useCase;

    private CreateSaleInputDTO dto;
    private List<ProductSaleDTO> saleProductsDTO;
    private Cashier cashier;
    private Sale sale;
    private List<ProductSale> saleProducts;
    private CashierMovements moviment;

    @BeforeEach
    void setUp() {

        cashier = Cashier.create("userId", 200.0);
        saleProductsDTO = List.of(
                new ProductSaleDTO("product_id", "produto 1", 4, 6, 5),
                new ProductSaleDTO("product_id", "produto 2", 5, 7, 5));
        dto = new CreateSaleInputDTO("userId", "clientId", cashier.getCashierId().getValue(), 65.0, 0.0,"paid","venda", saleProductsDTO);

        sale = Sale.create("userId", "clientId", cashier.getCashierId().getValue(), 100.0, 10.0, "paid");
        saleProducts = List.of(mock(ProductSale.class));
        moviment = mock(CashierMovements.class);
    }

    @Test
    @DisplayName("Deve criar uma venda paga com sucesso")
    void deveCriarVendaPagaComSucesso() {
        when(cashierRepository.getCashierById(dto.cashierId())).thenReturn(Optional.of(cashier));
        var result = useCase.execute(dto);
        System.out.println();
        assertEquals(65.0, result.partialPrice());
        assertEquals(65.0, result.total());
        assertEquals("paid", result.status());
        verify(salesRepository).save(any(Sale.class));
    }

    @Test
    @DisplayName("Deve criar uma venda paga com sucesso")
    void deveCriarUmaVendaPagaComDesconto() {
        final var createSaleDTO = new CreateSaleInputDTO("userId", "clientId", cashier.getCashierId().getValue(), 65.0, 5.0,"paid", "venda", saleProductsDTO);
        when(cashierRepository.getCashierById(createSaleDTO.cashierId())).thenReturn(Optional.of(cashier));
        var result = useCase.execute(createSaleDTO);
        assertEquals(65.0, result.partialPrice());
        assertEquals(5.0, result.discount());
        assertEquals(60.0, result.total());
        assertEquals("paid", result.status());
        verify(salesRepository).save(any(Sale.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se o caixa não for encontrado")
    void deveLancarExcecaoSeCaixaNaoForEncontrado() {
        when(cashierRepository.getCashierById(dto.cashierId())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> useCase.execute(dto));
        assertEquals("O caixa não existe", exception.getErrors().getFirst().message());

        verify(salesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção se o caixa estiver fechado")
    void deveLancarExcecaoSeCaixaFechado() {
        cashier.close("user_closed_id");
        when(cashierRepository.getCashierById(dto.cashierId())).thenReturn(Optional.of(cashier));

        var exception = assertThrows(ConflictException.class, () -> useCase.execute(dto));
        assertEquals("O caixa esta fechado", exception.getErrors().getFirst().message());

        verify(salesRepository, never()).save(any());
    }
    @Test
    @DisplayName("Deve lançar exceção se user_id for nulo")
    void deveLancarExcecaoSeUserIdForNulo() {
        var createSaleDTO = new CreateSaleInputDTO(null, "clientId", cashier.getCashierId().getValue(), 50.0, 0.0, "paid","venda", saleProductsDTO);
        when(cashierRepository.getCashierById(createSaleDTO.cashierId())).thenReturn(Optional.of(cashier));

        var exception = assertThrows(DomainException.class, () -> useCase.execute(createSaleDTO));
        assertTrue(exception.getErrors().stream().anyMatch(e -> e.message().equals("user_id não pode ser null")));
    }


    @Test
    @DisplayName("Deve lançar exceção se cashier_id for nulo")
    void deveLancarExcecaoSeCashierIdForNulo() {
        var createSaleDTO = new CreateSaleInputDTO("userId", "clientId", null, 50.0, 0.0, "paid", "venda", saleProductsDTO);

        var exception = assertThrows(DomainException.class, () -> useCase.execute(createSaleDTO));
        assertEquals(exception.getErrors().getFirst().message(), "O caixa não existe");
    }

    @Test
    @DisplayName("Deve lançar exceção se desconto for maior que o preço parcial")
    void deveLancarExcecaoSeDescontoMaiorQueParcial() {
        var createSaleDTO = new CreateSaleInputDTO("userId", "clientId", cashier.getCashierId().getValue(), 50.0, 60.0, "paid", "venda", saleProductsDTO);
        when(cashierRepository.getCashierById(createSaleDTO.cashierId())).thenReturn(Optional.of(cashier));

        var exception = assertThrows(DomainException.class, () -> useCase.execute(createSaleDTO));
        assertEquals("O desconto não pode ser maior que o preço parcial.", exception.getErrors().getFirst().message());
    }
}
