package com.starcode.erp_vendas_caixa.domain.aggregate;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;

public class AggregateRoot<EntityId extends Identifier> extends Entity<EntityId>{
    protected AggregateRoot(EntityId entity_id) {
        super(entity_id);
    }
     @Override
    public void validate(ValidationHandler validationHandler) {}
}
