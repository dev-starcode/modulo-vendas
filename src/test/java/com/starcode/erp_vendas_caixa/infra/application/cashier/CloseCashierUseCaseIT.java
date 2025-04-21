package com.starcode.erp_vendas_caixa.infra.application.cashier;

import com.starcode.erp_vendas_caixa.application.usecases.Cashier.CloseCashierUseCase;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CloseCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.infra.IntegrationTest;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierJPAEntity;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
public class CloseCashierUseCaseIT {

    @Autowired
    private CloseCashierUseCase closeCashierUseCase;

    @Autowired
    private CashierRepositoryJPA cashierRepository;


    @Test
    @DisplayName("Deve fechar um caixa")
    public void deveFecharUmCaixaCorretament(){
        final var cashier = Cashier.create("user_opened_id", 100D);
        cashierRepository.save(CashierJPAEntity.create(cashier));
        final var closeCashierDTO = new CloseCashierInputDTO(cashier.getCashierId().getValue(), "user_closed_id");
        final var output = this.closeCashierUseCase.execute(closeCashierDTO);
        assertEquals(output.cashierId(), cashier.getCashierId().getValue());
        assertEquals("closed", output.status());
    }

    @Test
    @DisplayName("Deve lançar uma exception caso o caixa não exista")
    public void deveLancarUmaExcecaoQuandoNaoEncotrarOCaixa(){
        var closeCashierDTO = new CloseCashierInputDTO("not_exists", "user_closed_id");
        final var output = assertThrows(NotFoundException.class,
                () -> this.closeCashierUseCase.execute(closeCashierDTO));
        assertEquals("Caixa não encontrado", output.getErrors().getFirst().message());
    }
}
