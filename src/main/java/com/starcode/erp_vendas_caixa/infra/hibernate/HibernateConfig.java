package com.starcode.erp_vendas_caixa.infra.hibernate;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("!test")
public class HibernateConfig {
    private final JpaProperties jpaProperties;
    private final String jpaPathPackagesToScan = "com.starcode.erp_vendas_caixa.*";

    public HibernateConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter (){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            final DataSource dataSource,
            final MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
            final CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl)
    {
        Map<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
        final var localContainer = new LocalContainerEntityManagerFactoryBean();
        localContainer.setDataSource(dataSource);
        localContainer.setPackagesToScan(jpaPathPackagesToScan);
        localContainer.setJpaVendorAdapter(jpaVendorAdapter());
        localContainer.setJpaPropertyMap(properties);
        return localContainer;
    }
}
