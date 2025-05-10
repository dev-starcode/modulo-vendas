package com.starcode.erp_vendas_caixa.infra.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
    private final DataSource dataSource;

    public MultiTenantConnectionProviderImpl(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(final Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(final Object tenantIdentifier) {
        try {
            final var connection = this.getAnyConnection();
            final var tenant = (String) tenantIdentifier;
            connection.createStatement().execute("set search_path to "+tenant);
            return connection;
        } catch (SQLException e){
            throw new HibernateException("Não foi possível setar o tenant "+tenantIdentifier);
        }
    }

    @Override
    public void releaseConnection(final Object tenantIdentifier, final Connection connection) throws SQLException {
        try (connection) {
            final var tenant = (String) tenantIdentifier;
            connection.createStatement().execute("set search_path to " + tenant);
        } catch (SQLException e) {
            throw new HibernateException("Erro ao resetar schema após uso de tenant " + tenantIdentifier);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
