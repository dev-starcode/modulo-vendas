package com.starcode.erp_vendas_caixa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@ComponentScan(basePackages = "com.starcode.erp_vendas_caixa")
public class ErpVendasCaixaApplication {

	public static void main(String[] args) {
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
		SpringApplication.run(ErpVendasCaixaApplication.class, args);
	}

}
