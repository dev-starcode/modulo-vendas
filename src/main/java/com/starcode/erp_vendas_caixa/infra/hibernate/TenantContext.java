package com.starcode.erp_vendas_caixa.infra.hibernate;

public class TenantContext {
    private static final String DEFAULT_TENANT = "test";
    private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(final String tenant){
        currentTenant.set(tenant);
    }
    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
