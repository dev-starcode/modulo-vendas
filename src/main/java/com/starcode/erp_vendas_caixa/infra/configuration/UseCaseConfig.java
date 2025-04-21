package com.starcode.erp_vendas_caixa.infra.configuration;

import com.starcode.erp_vendas_caixa.application.usecases.Cashier.*;
import com.starcode.erp_vendas_caixa.application.usecases.Sales.CreateSaleUseCase;
import com.starcode.erp_vendas_caixa.application.usecases.Sales.GetAllSalesByCashierUseCase;
import com.starcode.erp_vendas_caixa.application.usecases.Sales.GetSaleByIdUseCase;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierMovementsGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.ProductSaleGateway;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    private final CashierGateway cashierGateway;
    private final SaleGateway saleGateway;
    private final ProductSaleGateway productSaleRepository;
    private final CashierMovementsGateway cashierMovementsRepository;
    private final CashierGateway cashierRepository;

    public UseCaseConfig(CashierGateway cashierRepository, SaleGateway saleGateway, ProductSaleGateway productSaleRepository, CashierMovementsGateway cashierMovimentsRepository, CashierGateway cashierRepository1) {
        this.cashierGateway = cashierRepository;
        this.saleGateway = saleGateway;
        this.productSaleRepository = productSaleRepository;
        this.cashierMovementsRepository = cashierMovimentsRepository;
        this.cashierRepository = cashierRepository1;
    }

    @Bean
    public CreateCashierUseCase createCashierUseCase(){
        return new CreateCashierUseCase(cashierGateway);
    }
    @Bean
    public GetAllCashierUseCase getAllCashierUseCase(){return new GetAllCashierUseCase(cashierGateway);}
    @Bean
    public GetCashierById getCashierByIdUseCase(){
        return new GetCashierById(cashierGateway);
    }
    @Bean
    public OpenCashierUseCase openCashierUseCase(){return new OpenCashierUseCase(cashierGateway);}
    @Bean
    public CloseCashierUseCase closeCashierUseCase(){return new CloseCashierUseCase(cashierGateway, saleGateway);}
    @Bean
    CreateSaleUseCase createSaleUseCase(){return new CreateSaleUseCase(saleGateway, productSaleRepository, cashierMovementsRepository, cashierRepository);}
    @Bean
    GetAllSalesByCashierUseCase getAllSalesByCashierUseCase(){return new GetAllSalesByCashierUseCase(saleGateway);}
    @Bean
    GetSaleByIdUseCase getSaleByIdUseCase (){return new GetSaleByIdUseCase(saleGateway);}

}
