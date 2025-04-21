package com.starcode.erp_vendas_caixa.domain.value_objects;

import java.util.Objects;
import java.util.UUID;

public class Identifier extends ValueObject {

    private final String value;

    public Identifier(String value) {
        Objects.requireNonNull(value, "id não pode ser null");
        this.value = value;
    }

    public static Identifier unique(){
        return new Identifier(UUID.randomUUID().toString().toLowerCase());
    }
    public static Identifier restore(String id){
        return new Identifier(id);
    }
    public static Identifier restore(UUID id){
        return new Identifier(UUID.randomUUID().toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
