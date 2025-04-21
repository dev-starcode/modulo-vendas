package com.starcode.erp_vendas_caixa.domain.aggregate;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;
import java.util.Objects;

public abstract class Entity<EntityId extends Identifier> {
    protected final EntityId id;

    protected Entity(EntityId entitty_id) {
        Objects.requireNonNull(entitty_id, "'id' não pode ser null");
        this.id = entitty_id;
    }

    public EntityId getId() {
        return id;
    }


    public abstract void validate(ValidationHandler validationHandler);

    @Override
    public boolean equals(Object o) {
        if(this == o) return  true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
