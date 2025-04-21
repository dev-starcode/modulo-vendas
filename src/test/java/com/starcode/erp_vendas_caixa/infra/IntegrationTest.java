package com.starcode.erp_vendas_caixa.infra;

import com.starcode.erp_vendas_caixa.ErpVendasCaixaApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@ExtendWith(PostgresCleanUpExtension.class)
@SpringBootTest(classes = ErpVendasCaixaApplication.class)
public @interface IntegrationTest {
}
