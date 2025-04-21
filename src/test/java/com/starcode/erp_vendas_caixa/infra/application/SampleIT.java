package com.starcode.erp_vendas_caixa.infra.application;

import com.starcode.erp_vendas_caixa.application.usecases.Cashier.CreateCashierUseCase;
import com.starcode.erp_vendas_caixa.infra.IntegrationTest;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {
    @Autowired
    private CreateCashierUseCase createCashierUseCase;

    @Autowired
    private CashierRepositoryJPA cashierRepository;

    @Test
    public void test(){
        Assertions.assertNotNull(createCashierUseCase);
        Assertions.assertNotNull(cashierRepository);
    }
}
