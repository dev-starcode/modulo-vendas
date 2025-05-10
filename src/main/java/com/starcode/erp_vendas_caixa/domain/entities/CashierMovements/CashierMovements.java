package com.starcode.erp_vendas_caixa.domain.entities.CashierMovements;
import com.starcode.erp_vendas_caixa.domain.aggregate.AggregateRoot;
import com.starcode.erp_vendas_caixa.domain.enums.TypeCashierMovements;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;
import com.starcode.erp_vendas_caixa.domain.value_objects.Price;
import java.time.LocalDateTime;

public class CashierMovements extends AggregateRoot<Identifier> {
    private String cashierId;
    private String userId;
    private TypeCashierMovements type;
    private Price amount;
    private String reason;
    private LocalDateTime createdAt;

    private CashierMovements(Identifier cashier_movement_id, String cashier_id, String user_id, TypeCashierMovements type, Price amount, String reason, LocalDateTime created_at) {
        super(cashier_movement_id);
        this.cashierId = cashier_id;
        this.userId = user_id;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = created_at;
    }

    @Override
    public void validate(ValidationHandler validationHandler) {
        new CashierMovimentsValidator(this, validationHandler).validate();
    }

    public static CashierMovements create(final String cashier_id, final String user_id, final String type, final Double amount, final String reason) {
        final var cashier_movement_id = Identifier.unique();
        final var created_at = LocalDateTime.now();
        return new CashierMovements(cashier_movement_id, cashier_id, user_id, TypeCashierMovements.fromString(type), Price.validate(amount), reason, created_at);
    }

    public static CashierMovements restore(final String cashier_movement_id, final String cashier_id, final String user_id, final String type, final Double amount, final String reason, final LocalDateTime created_at){
        final var id = Identifier.restore(cashier_movement_id);
        return new CashierMovements(id, cashier_id, user_id, TypeCashierMovements.fromString(type), Price.restore(amount), reason, created_at);
    }

    public void in() {
        if(this.type == TypeCashierMovements.IN) throw DomainException.with(Error.create("Conflict","O tipo de movimentação já é de entrada"));
        this.type = TypeCashierMovements.IN;
    }
    public void out(){
        if(this.type == TypeCashierMovements.OUT) throw DomainException.with(Error.create("Conflict", "O tipo de movimentação já é de saída"));
        this.type = TypeCashierMovements.OUT;
    }
    public Identifier getCashierMovementId() {
        return this.id;
    }

    public String getCashierId() {
        return cashierId;
    }

    public String getUserId() {
        return userId;
    }

    public TypeCashierMovements getType() {
        return type;
    }

    public Price getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
