package com.starcode.erp_vendas_caixa.infra.cashier.persistence.database;

import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;
import com.starcode.erp_vendas_caixa.infra.productSale.persistence.ProductSaleRepositoryJPA;
import com.starcode.erp_vendas_caixa.infra.productSale.persistence.database.ProductSalePostgresqlRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductSalePostgresqlRepositoryTest {

    @Autowired
    private ProductSalePostgresqlRepository productSalePostgresqlRepository;

    @Autowired
    private ProductSaleRepositoryJPA productSaleRepositoryJPA;

    @BeforeAll
    void setup(){
        final var saleProduct1 = ProductSale.restore(
                "saleProduct1", "sale123", "product123", "Produto Restaurado", 15.0, 30.0, 3, 90.0
        );
        final var saleProduct2 = ProductSale.restore(
                "saleProduct2", "sale123", "product123", "Produto Restaurado", 15.0, 30.0, 3, 90.0
        );
        productSalePostgresqlRepository.save(saleProduct1);
        productSalePostgresqlRepository.save(saleProduct2);
    }

    @Test
    @DisplayName("Deve salvar uma venda de produto")
    public void deveSalvarUmCaixa(){
        final var saleProduct = ProductSale.restore(
                "saleProduct", "sale123", "product123", "Produto Restaurado", 15.0, 30.0, 3, 90.0
        );
        this.productSalePostgresqlRepository.save(saleProduct);
        Assertions.assertEquals(3, productSaleRepositoryJPA.count());
    }

    @Test
    @DisplayName("Deve buscar uma venda de produto")
    public void deveBuscarUmCaixa(){

        final var result = this.productSalePostgresqlRepository.getProductSaleById("saleProduct1");
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("saleProduct1", result.get().getSale_product_id().getValue());
    }
}
